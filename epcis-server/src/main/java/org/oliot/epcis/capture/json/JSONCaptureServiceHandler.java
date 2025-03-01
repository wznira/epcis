package org.oliot.epcis.capture.json;

import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.Router;

import org.oliot.epcis.model.EPCISException;
import org.oliot.epcis.pagination.Page;
import org.oliot.epcis.server.EPCISServer;
import org.oliot.epcis.util.HTTPUtil;

import java.util.UUID;

import static org.oliot.epcis.validation.HeaderValidator.*;

/**
 * Copyright (C) 2020-2023. (Jaewook Byun) all rights reserved.
 * <p>
 * This project is an open source implementation of Electronic Product Code
 * Information Service (EPCIS) v2.0,
 * <p>
 * XMLCaptureServiceHandler holds routers for capture services.
 * <p>
 *
 * @author Jaewook Byun, Ph.D., Assistant Professor, Sejong University,
 *         jwbyun@sejong.ac.kr, Associate Director, Auto-ID Labs, Korea,
 *         bjw0829@gmail.com
 */
public class JSONCaptureServiceHandler {

	/**
	 * EPCIS events are added in bulk using the capture interface. Four design
	 * considerations were made to remain compatible with EPCIS 1.2:\n EPCIS 2.0
	 * keeps event IDs optional. If event IDs are missing, the server should
	 * populate the event ID with a unique value.\n Otherwise, it won't be possible
	 * to retrieve these events by eventID.\n - By default, EPCIS events are only
	 * stored if the entire capture job was successful. This behaviour can be
	 * changed with the `GS1-Capture-Error-Behaviour` header.\n- EPCIS master data
	 * can be captured in the header (`epcisHeader`) of an `EPCISDocument`.\n - This
	 * endpoint should support both `EPCISDocument` and `EPCISQueryDocument` as
	 * input.\n To prevent timeouts for large payloads, the client potentially may
	 * need to split the payload into several capture calls. To that end, the server
	 * can specify a capture\nlimit (number of EPCIS events) and file size limit
	 * (payload size).\n A successful capturing of events does not guarantee that
	 * events will be stored. Instead, the server returns a\ncapture id, which the
	 * client can use to obtain information about the capture job.\n"
	 *
	 * @param router             router
	 * @param jsonCaptureService jsonCaptureService
	 * @param eventBus           eventBus
	 */
	public static void registerPostCaptureHandler(Router router, JSONCaptureService jsonCaptureService,
			EventBus eventBus) {

		router.post("/epcis/capture").consumes("application/json").handler(routingContext -> {
			if (!isEqualHeaderREST(routingContext, "GS1-EPCIS-Version"))
				return;
			if (!isEqualHeaderREST(routingContext, "GS1-CBV-Version"))
				return;
			if (!isEqualHeaderREST(routingContext, "GS1-EPCIS-Capture-Error-Behaviour"))
				return;

			jsonCaptureService.post(routingContext, eventBus);
		});
		EPCISServer.logger.info("[POST /epcis/capture (application/json)] - router added");
	}

	/**
	 * Returns information about the capture job.
	 *
	 * @param router             router
	 * @param jsonCaptureService jsonCaptureService
	 */
	public static void registerGetCaptureIDHandler(Router router, JSONCaptureService jsonCaptureService) {
		router.get("/epcis/capture/:captureID").consumes("application/json").handler(routingContext -> {
			if (!checkEPCISMinMaxVersion(routingContext))
				return;
			jsonCaptureService.postCaptureJob(routingContext, routingContext.pathParam("captureID"));
		});
		EPCISServer.logger.info("[GET /epcis/capture/:captureID (application/json)] - router added");
	}

	/**
	 * Synchronous capture interface for a single EPCIS event. An individual EPCIS
	 * event can be created by making a `POST` request on the `/events` resource.
	 * Alternatively, the client can also use the `/capture` interface and capture a
	 * single event.
	 *
	 * @param router            router
	 * @param xmlCaptureService xmlCaptureService
	 */
	public static void registerPostEventsHandler(Router router, JSONCaptureService jsonCaptureService,
			EventBus eventBus) {
		router.post("/epcis/events").consumes("*/json").blockingHandler(routingContext -> {
			if (!isEqualHeaderREST(routingContext, "GS1-EPCIS-Version"))
				return;
			if (!isEqualHeaderREST(routingContext, "GS1-CBV-Version"))
				return;
			jsonCaptureService.postEvent(routingContext, eventBus);
		});
		EPCISServer.logger.info("[POST /epcis/events (application/json)] - router added");
	}

	/**
	 * non-standard to provide validation service
	 *
	 * @param router             router
	 * @param jsonCaptureService jsonCaptureService
	 */
	public static void registerValidationHandler(Router router, JSONCaptureService jsonCaptureService) {
		router.post("/epcis/validation").consumes("*/json").handler(jsonCaptureService::postValidationResult);
		EPCISServer.logger.info("[POST /epcis/validation (application/json)] - router added");
	}

	/**
	 * Returns a list of capture jobs. When EPCIS events are added through the
	 * capture interface, the capture process can run asynchronously. If the payload
	 * is syntactically correct and the client is allowed to call `/capture`, the
	 * server returns a `202` HTTP response code. This endpoint returns all capture
	 * jobs that were created and supports pagination.
	 *
	 * @param router             router
	 * @param jsonCaptureService jsonCaptureService
	 */
	public static void registerGetCaptureHandler(Router router, JSONCaptureService jsonCaptureService) {
		router.get("/epcis/capture").consumes("application/json").handler(routingContext -> {
			if (!checkEPCISMinMaxVersion(routingContext))
				return;
			String nextPageToken = routingContext.request().getParam("NextPageToken");
			if (nextPageToken == null) {
				jsonCaptureService.postCaptureJobList(routingContext);
			} else {
				jsonCaptureService.postRemainingCaptureJobList(routingContext, nextPageToken);
			}
		});
		EPCISServer.logger.info("[GET /epcis/capture (application/json)] - router added");
	}

	/**
	 * Optional endpoint that allows on-demand release of any resources associated
	 * with `nextPageToken`.
	 *
	 * @param router
	 */
	public static void registerDeletePageToken(Router router) {
		router.delete("/epcis/nextPageToken/:token").consumes("application/json").handler(routingContext -> {
			UUID uuid = UUID.fromString(routingContext.pathParam("token"));
			Page page = EPCISServer.captureIDPageMap.remove(uuid);
			try {
				page.getTimer().cancel();
			} catch (Exception e) {

			}
			if (page != null) {
				routingContext.response().setStatusCode(204).end();
			} else {
				EPCISException e = new EPCISException("There is no page with token: " + uuid.toString());
				HTTPUtil.sendQueryResults(routingContext.response(),
						JSONMessageFactory.get404NoSuchResourceException(e.getMessage()), 404);
			}
		});
		EPCISServer.logger.info("[DELETE /nextPageToken/:token (application/json)] - router added");
	}
}
