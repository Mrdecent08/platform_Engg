package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/swagger-ui")
public class SwaggerController {

	@GetMapping("/efk-logging")
	public String swaggerefk(){
		String host="efk-ms-service.efk.svc.cluster.local";
		String port="31597";
		return "http://"+host+":"+port+"/swagger-ui/index.html";
	}
}
