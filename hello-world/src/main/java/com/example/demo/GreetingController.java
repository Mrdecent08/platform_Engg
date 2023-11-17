package com.example.demo;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
	
	@GetMapping(value = "/hello")
	public String getHello() {
		return "Hello world!";
	}
	
	@GetMapping(value= "/secret")
	public Map<String, String> getSecret() {
	    final String SECRET_STORE_NAME = "kubernetessecretstore";
		DaprClient client = new DaprClientBuilder().build();
        System.out.println("Created Dapr Client");
        Map<String, String> secret = client.getSecret(SECRET_STORE_NAME, "secret").block();
        System.out.println("Fetched Secret: " + secret);
        return secret;
	}
	
}