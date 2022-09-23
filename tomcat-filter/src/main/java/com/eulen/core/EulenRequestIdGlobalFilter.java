package com.eulen.core;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.slf4j.MDC;
//import org.slf4j.MDC;

/**
 * Filtro Global que captura las peticiones http mediante filtro tomcat Valve Se
 * configura en el fichero %TOMCAT_HOME%\conf\server.xml
 * <Valve className="com.eulen.core.EulenRequestIdGlobalFilter"/> *
 * 
 * @author skanc
 *
 */
public class EulenRequestIdGlobalFilter extends ValveBase {

	/**
	 * Cabeceras HTTP utilizadas para almecenar el CorrelationId
	 */
	public static final String CORRELATION_ID_FIELD_NAME = "X-Correlation-Id";
	public static final String CORRELATION_ID_BY_FIELD_NAME = "X-Correlation-Id-By";
	public static final String CONTADOR_FIELD = "contador";

	// Variable de trabajo para analizar rendimiento
	public static int Contador = 0;

	public static synchronized int Counter() {
		Contador++;
		return Contador;
	}

	// Log
	private static Logger logger = Logger.getLogger(EulenRequestIdGlobalFilter.class.getName());

	/**
	 * Opcion de Manual de MDC
	 */
	private static ThreadLocal<String> correlationIdStore = new ThreadLocal<String>();

	public static String getCorrelationId() {
		return correlationIdStore.get();
	}

	@Override
	public void invoke(Request request, Response response) throws IOException, ServletException {

		// Contador Test
		int Contador = EulenRequestIdGlobalFilter.Counter();
		logger.info("Contador: " + Contador);

		// Do your filter/setting header in response
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Object header = httpRequest.getHeader(CORRELATION_ID_FIELD_NAME);
		String correlationId = (header != null) ? header.toString() : UUID.randomUUID().toString();
		MDC.put(CORRELATION_ID_FIELD_NAME, correlationId);
		logger.info("MDCAdapter Instance: " + MDC.getMDCAdapter().toString());
		correlationIdStore.set(correlationId);
		// Setear atributos en la peticion
		request.setAttribute(CORRELATION_ID_FIELD_NAME, correlationId);
		request.setAttribute(CONTADOR_FIELD, Contador);
		request.setAttribute(CORRELATION_ID_BY_FIELD_NAME, String.format("%s-%s",
				EulenRequestIdGlobalFilter.class.getName(), getClass().getPackage().getImplementationVersion()));

		logger.info(String.format("Global Eulen Filter CorrelationId: %s", correlationId));

		getNext().invoke(request, response);
	}

}