package com.eulen.correlationid;

import org.slf4j.MDC;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * Facilitador para las comumicaciones entre servicios
 * 
 * @author skanc
 *
 */
public class WebClient {

	/**
	 * Envia una peticion HTTP
	 * 
	 * @param url URL donde se envia la peticion
	 * @return retorna la respuesta de la petición en formato texto
	 */
	public static String sendHttp(String url) {
		org.springframework.web.reactive.function.client.WebClient client = org.springframework.web.reactive.function.client.WebClient
				.create();
		String responseBody = client.get().uri(url).header("X-Correlation-Id", MDC.get("X-Correlation-Id")).retrieve()
				.bodyToMono(String.class).block();
		return responseBody;
	}

	/**
	 * Envia una peticion HTTP
	 * 
	 * @param <T>   Clase retorno de la peticion
	 * @param url   URL donde se envia la peticion
	 * @param clazz
	 * @return retorna la respuesta de la petición
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public static <T> T sendHttpToObject(String url, Class<T> clazz)
			throws JsonMappingException, JsonProcessingException {
		org.springframework.web.reactive.function.client.WebClient client = org.springframework.web.reactive.function.client.WebClient
				.create();
		T responseBody = client.get().uri(url).header("X-Correlation-Id", MDC.get("X-Correlation-Id")).retrieve()
				.bodyToMono(clazz).block();

		return responseBody;
	}

}
