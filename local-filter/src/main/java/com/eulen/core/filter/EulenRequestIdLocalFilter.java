package com.eulen.core.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;;

@Component
public class EulenRequestIdLocalFilter implements Filter {
	public static final String CORRELATION_ID_FIELD_NAME = "X-Correlation-Id";
	public static final String CONTADOR_FIELD = "contador";

	Logger log = LogManager.getLogger(EulenRequestIdLocalFilter.class);

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		log.info("Eulen Local Filter doFilter");
		Object header = httpRequest.getHeader(CORRELATION_ID_FIELD_NAME);
		String correlationAttribute = (String) request.getAttribute(CORRELATION_ID_FIELD_NAME);
		Integer contador = (Integer) request.getAttribute(CONTADOR_FIELD);
		String correlationHeader = (header != null) ? header.toString() : null;
		String correlationMDC = MDC.get(CORRELATION_ID_FIELD_NAME);
		MDC.put(CORRELATION_ID_FIELD_NAME, coalesceCorrelationID(correlationHeader, correlationAttribute));
		MDC.put(CONTADOR_FIELD, "" + contador);
		if (correlationAttribute == null) {
			log.warn("Tomcat Server hasn't Eulen Filter CorrelationId. This filter is necessary in production server");
			MDC.put(CORRELATION_ID_FIELD_NAME, UUID.randomUUID().toString());
			log.warn(String.format("Generate RequestId Temporal (%s)", MDC.get(CORRELATION_ID_FIELD_NAME)));
		}
		log.info("MDCAdapter Instance: " + MDC.getMDCAdapter().toString());
		log.debug("Contador: " + contador);
		log.info(String.format("Filter : %s : %s : %s", correlationHeader, correlationMDC, correlationAttribute));
		filterchain.doFilter(request, response);
	}

//   public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain) 
//		      throws IOException, ServletException {
//		      HttpServletRequest httpRequest = (HttpServletRequest) request;
//		      Object header = httpRequest.getHeader(HTTP_CORRELATION_ID_HEADER);
//		      if (header != null) {
//		         String correlationId = header.toString();
//		         CorrelationIdStore.setRequest(correlationId);
//		      }else {
//		         CorrelationIdStore.newRequest();
//		      } 
//		      MDC.put("correlationId", CorrelationIdStore.getCurrentId());
//		      log.info("Filter");
//		      /*System.out.println("Remote Host:"+request.getRemoteHost());
//		      System.out.println("Remote Address:"+request.getRemoteAddr());*/
//		      
//		      filterchain.doFilter(request, response);
//		   }
	@Override
	public void init(FilterConfig filterconfig) throws ServletException {
		log.info("Eulen Local Filter Activate");
	}

	/**
	 * Funcion para extraer el primer campo que no sea nulo
	 * 
	 * @param args
	 * @return
	 */
	private String coalesceCorrelationID(String... args) {
		for (String s : args) {
			if (s != null && !s.isEmpty())
				return s;
		}
		log.warn(
				"Tomcat don't set CorrelationID into anywere. See the documentation of Eulen to set de Filter into Tomcat Server");

		return null;
	}
}