package com.novice.framework.cloud.commons.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novice.framework.cloud.commons.exception.ValidationException;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

public final class RestTemplateHelper {
	private static final RestTemplate REST_TEMPLATE = new RestTemplate();

	private RestTemplateHelper() {
	}

	public static RestTemplate getRestTemplate() {
		return REST_TEMPLATE;
	}

	public static void post(String url, Object... uriVariables) {
		REST_TEMPLATE.execute(url, HttpMethod.POST, null, null, uriVariables);
	}

	public static <T> T getForGeneric(String url) {
		return getForTypeReference(url, new TypeReference<>() {
		});
	}

	public static <T> T getForTypeReference(String url, TypeReference<T> typeReference) {
		String result = REST_TEMPLATE.getForObject(url, String.class);
		if (StringUtils.isEmpty(result)) {
			return null;
		}
		try {
			return new ObjectMapper().readValue(result, typeReference);
		} catch (JsonProcessingException e) {
			throw new ValidationException(e);
		}
	}

}
