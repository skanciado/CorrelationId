package com.eulen.core.spring.utils;

import org.slf4j.MDC;

import com.eulen.core.filter.EulenRequestIdLocalFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class WebClient {

	public static String sendHttp(String url) {
		org.springframework.web.reactive.function.client.WebClient client = org.springframework.web.reactive.function.client.WebClient
				.create();
		String responseBody = client.get().uri(url)
				.header(EulenRequestIdLocalFilter.CORRELATION_ID_FIELD_NAME,
						MDC.get(EulenRequestIdLocalFilter.CORRELATION_ID_FIELD_NAME))
				.retrieve().bodyToMono(String.class).block();
		return responseBody;
	}

	public static <T> T sendHttpToObject(String url, Class<T> clazz)
			throws JsonMappingException, JsonProcessingException {
		org.springframework.web.reactive.function.client.WebClient client = org.springframework.web.reactive.function.client.WebClient
				.create();
		T responseBody = client.get().uri(url)
				.header(EulenRequestIdLocalFilter.CORRELATION_ID_FIELD_NAME,
						MDC.get(EulenRequestIdLocalFilter.CORRELATION_ID_FIELD_NAME))
				.retrieve().bodyToMono(clazz).block();

		return responseBody;
	}
}
