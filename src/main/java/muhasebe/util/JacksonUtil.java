package muhasebe.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JacksonUtil {

	@Autowired
	ObjectMapper objectMapper;

	public String convertObjectToJson(Object object) throws JsonProcessingException {
		if (ObjectUtils.isEmpty(object)) {
			return null;
		}
		return objectMapper.writeValueAsString(object);
	}

	public String convertObjectListToJson(List<Object> objectList) throws JsonProcessingException {
		if (CollectionUtils.isEmpty(objectList)) {
			return null;
		}
		return objectMapper.writeValueAsString(objectList);
	}

	public Map<String, Object> getMapByJsonString(String jsonString) {

		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			return objectMapper.readValue(jsonString, HashMap.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
