package com.eulen.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.reactive.function.client.WebClient;

import com.eulen.controller.entities.ResultDemoStatus;
import com.eulen.correlationid.WebClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Controller base para realizar la demo
 * 
 * @author skanc
 *
 */
@RestController
public class RestCorrelationIdController {
	Logger log = LogManager.getLogger(RestCorrelationIdController.class);

	@GetMapping("/correlationId")
	ResultDemoStatus correlationId() {
		ResultDemoStatus status = new ResultDemoStatus();
		log.info("/correlationId");
		status.addState();
		return status;
	}

	@GetMapping("/send")
	ResultDemoStatus send(@RequestParam String url) throws JsonMappingException, JsonProcessingException {
		ResultDemoStatus status = new ResultDemoStatus();
		log.info("/send?url=" + url);
		status.addState();
		String json = WebClient.sendHttp(url);
		status.addState();
		ObjectMapper objectMapper = new ObjectMapper();
		status.addState();
		ResultDemoStatus obj = objectMapper.readValue(json, ResultDemoStatus.class);
		status.addState();
		status.merge(obj);
		// ResultDemoStatus status2 = WebClient.sendHttpToObject(url,
		// ResultDemoStatus.class);
		status.addState();
		return status;
	}

	@GetMapping("/correlationId2")
	ResultDemoStatus correlationId2() throws InterruptedException {
		ResultDemoStatus status = new ResultDemoStatus();
		log.info("/correlationId2");
		status.addState();
		Thread.sleep(1000);
		status.addState();
		return status;
	}

	// Create a get request to the url and return the response bodyToMono object
	@GetMapping("/send2")
	ResultDemoStatus send2(@RequestParam String url) throws JsonMappingException, JsonProcessingException {
		ResultDemoStatus status = new ResultDemoStatus();
		log.info("/send?url=" + url);
		status.addState();
		String json = WebClient.sendHttp(url);
		status.addState();
		ObjectMapper objectMapper = new ObjectMapper();
		status.addState();
		ResultDemoStatus obj = objectMapper.readValue(json, ResultDemoStatus.class);
		status.addState();
		status.merge(obj);
		// ResultDemoStatus status2 = WebClient.sendHttpToObject(url,
		// ResultDemoStatus.class);
		status.addState();
		return status;
	}

}