package com.example.demo;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;

@RestController
public class secretController {

	final String SECRET_STORE_NAME = "localsecretstore";
	DaprClient client = new DaprClientBuilder().build();
	
	@GetMapping("/{key}")
	public String getSecret(@PathVariable String secretKey) {
        Map<String, String> secret = client.getSecret(SECRET_STORE_NAME, secretKey).block();
        return "Fetched Secret: " + secret;
	}
}
