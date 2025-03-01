package org.oliot.epcis.converter.data.pojo_to_bson;

import static org.oliot.epcis.resource.StaticResource.*;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.oliot.epcis.model.AttributeType;
import org.oliot.epcis.model.ValidationException;
import org.oliot.epcis.model.VocabularyElementType;
import org.oliot.epcis.model.VocabularyType;
import org.oliot.epcis.tdt.TagDataTranslationEngine;
import org.oliot.epcis.util.CBVAttributeUtil;

import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;

/**
 * Copyright (C) 2020-2023. (Jaewook Byun) all rights reserved.
 * <p>
 * This project is an open source implementation of Electronic Product Code
 * Information Service (EPCIS) v2.0,
 * <p>
 * The class converts MasterData from a storage unit to POJO.
 * <p>
 *
 * @author Jaewook Byun, Ph.D., Assistant Professor, Sejong University,
 *         jwbyun@sejong.ac.kr, Associate Director, Auto-ID Labs, Korea,
 *         bjw0829@gmail.com
 */
public class MasterDataConverter {

	public static void checkVocabularyTypeID(String type, String id) throws ValidationException {
		if (type.equals("urn:epcglobal:epcis:vtype:ReadPoint")) {
			TagDataTranslationEngine.checkLocationEPCPureIdentity(gcpLength, id);
		} else if (type.equals("urn:epcglobal:epcis:vtype:BusinessLocation")) {
			TagDataTranslationEngine.checkLocationEPCPureIdentity(gcpLength, id);
		} else if (type.equals("urn:epcglobal:epcis:vtype:BusinessTransaction")) {
			TagDataTranslationEngine.checkBusinessTransactionEPCPureIdentity(gcpLength, id);
		} else if (type.equals("urn:epcglobal:epcis:vtype:EPCClass")) {
			TagDataTranslationEngine.checkEPCClassPureIdentity(gcpLength, id);
		} else if (type.equals("urn:epcglobal:epcis:vtype:SourceDest")) {
			TagDataTranslationEngine.checkSourceDestinationEPCPureIdentity(gcpLength, id);
		} else if (type.equals("urn:epcglobal:epcis:vtype:Location")) {
			TagDataTranslationEngine.checkLocationEPCPureIdentity(gcpLength, id);
		} else if (type.equals("urn:epcglobal:epcis:vtype:Party")) {
			TagDataTranslationEngine.checkPartyEPCPureIdentity(gcpLength, id);
		} else if (type.equals("urn:epcglobal:epcis:vtype:Microorganism")) {
			TagDataTranslationEngine.checkMicroorganismValue(id);
		} else if (type.equals("urn:epcglobal:epcis:vtype:ChemicalSubstance")) {
			TagDataTranslationEngine.checkChemicalSubstance(id);
		} else if (type.equals("urn:epcglobal:epcis:vtype:Resource")) {
			TagDataTranslationEngine.checkDocumentEPCPureIdentity(gcpLength, id);
		} else {
			throw new ValidationException(type + " should be one of " + vocabularyTypes.toString());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<WriteModel<Document>> toBson(VocabularyType vocabulary) throws ValidationException {

		final String type = vocabulary.getType();
		List<WriteModel<Document>> list = new ArrayList<WriteModel<Document>>();
		for (VocabularyElementType ve : vocabulary.getVocabularyElementList().getVocabularyElement()) {
			Document find = new Document();
			String id = ve.getId();
			// check id is compatible with type
			checkVocabularyTypeID(type, id);
			find.put("id", id);
			find.put("type", type);
			Document update = new Document();
			Document attrObj = new Document();
			// for each attribute
			for (AttributeType a : ve.getAttribute()) {
				try {
					CBVAttributeUtil.checkCBVCompliantAttribute(a.getId(), a.getOtherAttributes(), a.getContent(),
							attrObj);
				} catch (ValidationException e) {
					throw e;
				}

				String attrKey = POJOtoBSONUtil.encodeMongoObjectKey(a.getId());
				if (a.getContent().size() == 1 && a.getContent().get(0) instanceof String) {
					if (!attrObj.containsKey(attrKey)) {
						// first occurrence

						attrObj.put(attrKey, a.getContent().get(0).toString().trim());
					} else if (attrObj.containsKey(attrKey) && !(attrObj.get(attrKey) instanceof List)) {
						// second occurrence
						Object firstObj = attrObj.remove(attrKey);
						ArrayList arr = new ArrayList();
						arr.add(firstObj);
						arr.add(a.getContent().get(0).toString().trim());
						attrObj.put(attrKey, arr);
					} else {
						// third or more occurrence
						List arr = attrObj.get(attrKey, List.class);
						arr.add(a.getContent().get(0).toString().trim());
						attrObj.put(attrKey, arr);
					}

				} else {

					Document any = null;
					try {
						any = POJOtoBSONUtil.putAny(new Document(), "extension", a.getContent(), true);
					} catch (ValidationException e) {
						throw new RuntimeException(e.getCause());
					}

					if (!attrObj.containsKey(attrKey)) {
						// first occurrence
						attrObj.put(attrKey, any);
					} else if (attrObj.containsKey(attrKey) && !(attrObj.get(attrKey) instanceof List)) {
						// second occurrence
						Object firstObj = attrObj.remove(attrKey);
						ArrayList arr = new ArrayList();
						arr.add(firstObj);
						arr.add(any);
						attrObj.put(attrKey, arr);
					} else {
						// third or more occurrence
						List arr = attrObj.get(attrKey, List.class);
						arr.add(any);
						attrObj.put(attrKey, arr);
					}

				}
			}
			attrObj.put("lastUpdate", System.currentTimeMillis());
			Document updateElement = new Document();
			updateElement.put("attributes", attrObj);
			try {
				ArrayList childArray = new ArrayList();
				if (ve.getChildren() != null) {
					ve.getChildren().getId().parallelStream().forEach(c -> {
						synchronized (childArray) {
							childArray.add(c);
						}
					});
				}
				updateElement.put("children", childArray);

			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			update.append("$set", updateElement);

			list.add(new UpdateOneModel<Document>(find, update, new UpdateOptions().upsert(true)));
		}
		return list;
	}
}
