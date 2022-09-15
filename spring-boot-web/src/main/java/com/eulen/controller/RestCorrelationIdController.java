package com.eulen.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.reactive.function.client.WebClient;

import com.eulen.correlationid.CorrelationIdFilter;
import com.eulen.correlationid.CorrelationIdStore;
import com.eulen.correlationid.WebClient;
import com.eulen.logger.ILogger;
import com.eulen.logger.LoggerLog4J;

@RestController
public class RestCorrelationIdController {
    ILogger log = LoggerLog4J.getLogger(RestCorrelationIdController.class);
    @GetMapping("/correlationId")
    String correlationId() {
        log.debug("/correlationId");
        return "CorrelationId: " + CorrelationIdStore.getCurrentId();
    }
    @GetMapping("/send")
    String send(@RequestParam String url) {
        log.debug("/send?url=" + url); 
        return "Body Request: " + WebClient.sendHttp(url);
    }
    // Create a get request to the url and return the response bodyToMono object
     @GetMapping("/send2")
     String send2(@RequestParam String url) {
         log.debug("/send?url=" + url);
         org.springframework.web.reactive.function.client.WebClient client = org.springframework.web.reactive.function.client.WebClient.create();
         String responseBody = client.get()
             .uri(url).header(CorrelationIdFilter.HTTP_CORRELATION_ID_HEADER, CorrelationIdStore.getCurrentId())
             .retrieve().bodyToMono(String.class).block();
         return "Body Request: " + responseBody;
     }

}