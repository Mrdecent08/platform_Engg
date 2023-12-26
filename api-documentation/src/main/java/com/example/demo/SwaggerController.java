package com.example.demo;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/swagger-ui")
public class SwaggerController {

	@GetMapping("/efk-logging")
	public String swaggerefk() throws UnknownHostException{
		
		@Value("${efk-ms-service.host}")
		String hostname ;
		
		String port="31597";
		return "http://"+hostname+":"+port+"/swagger-ui/index.html";
	}
}
