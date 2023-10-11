package org.oliot.epcis.query.converter.seq;

import java.util.List;

import org.bson.Document;
import org.oliot.epcis.model.QueryParameterException;
import org.oliot.epcis.model.ValidationException;
import org.oliot.epcis.query.converter.BaseConverter;
import org.oliot.epcis.query.converter.QueryConverter;
import org.oliot.epcis.resource.StaticResource;
import org.oliot.epcis.tdt.TagDataTranslationEngine;

public class EQSensorMetadataDeviceMetadataConverter extends BaseConverter implements QueryConverter {

	@Override
	public Document convert(String key, Object value) throws QueryParameterException {
		List<String> valueList = getListOfString(value);
		for (String v : valueList) {
			try {
				TagDataTranslationEngine.checkEPCPureIdentity(StaticResource.gcpLength, v);
			} catch (ValidationException e) {
				throw new QueryParameterException(e.getReason());
			}
		}
		return getEQQuery("sensorElementList.sensorMetadata.deviceMetadata", valueList);
	}
}
