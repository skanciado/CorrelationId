package com.eulen.correlationid;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import com.eulen.logger.ILogger;
import com.eulen.logger.LoggerLog4J;

import jakarta.servlet.Filter;

import java.io.IOException;
 
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest; ;

@Component
public class CorrelationIdFilter implements Filter {
   public static final String HTTP_CORRELATION_ID_HEADER = "X-Correlation-Id";
   ILogger log = LoggerLog4J.getLogger(CorrelationIdFilter.class);
   @Override
   public void destroy() {} 

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain) 
      throws IOException, ServletException {
      HttpServletRequest httpRequest = (HttpServletRequest) request;
      Object header = httpRequest.getHeader(HTTP_CORRELATION_ID_HEADER);
      if (header != null) {
         String correlationId = header.toString();
         CorrelationIdStore.setRequest(correlationId);
      }else {
         CorrelationIdStore.newRequest();
      } 
      MDC.put("correlationId", CorrelationIdStore.getCurrentId());
      log.info("Filter");
      /*System.out.println("Remote Host:"+request.getRemoteHost());
      System.out.println("Remote Address:"+request.getRemoteAddr());*/
      
      filterchain.doFilter(request, response);
   }

   @Override
   public void init(FilterConfig filterconfig) throws ServletException {}
}