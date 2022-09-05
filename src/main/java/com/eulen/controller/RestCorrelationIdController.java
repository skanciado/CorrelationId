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
        log.info("/correlationId");
        return "CorrelationId: " + CorrelationIdStore.getCurrentId();
    }
    @GetMapping("/send")
    String send(@RequestParam String url) {
        log.info("/send?url=" + url); 
        // WebClient client = WebClient.create();
        // String responseBody = client.get()
        //     .uri(url).header(CorrelationIdFilter.HTTP_CORRELATION_ID_HEADER, CorrelationIdStore.getCurrentId())
        //     .retrieve().bodyToMono(String.class).block();

     
        return "Body Request: " + WebClient.sendHttp(url);
    }
}