package com.example.demo;

import java.net.InetAddress;
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
		
		String serviceHostname = "efk-ms-service.efk.svc.cluster.local";
		InetAddress inetAddress = InetAddress.getByName(serviceHostname);
		String resolvedHostname = inetAddress.getHostName();
		String port="31597";
		return "http://"+resolvedHostname+":"+port+"/swagger-ui/index.html";
	}
}
