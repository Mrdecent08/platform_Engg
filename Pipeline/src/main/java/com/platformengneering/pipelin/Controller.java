package com.platformengneering.pipelin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/pipeline")
public class Controller {
	@Autowired
	private Service service;

@PostMapping("/job1")
public String triggerPipeline(@RequestBody JsonNode body) {
	return service.triggerPipeline(body);
}
}