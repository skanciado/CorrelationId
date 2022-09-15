 package com.eulen.correlationid;

 

public class WebClient {

     
    public static String sendHttp (String url) {
        org.springframework.web.reactive.function.client.WebClient client = org.springframework.web.reactive.function.client.WebClient.create();
        String responseBody = client.get()
            .uri(url).header(CorrelationIdFilter.HTTP_CORRELATION_ID_HEADER, CorrelationIdStore.getCurrentId())
            .retrieve().bodyToMono(String.class).block();
        return responseBody;
    }
     

 }






