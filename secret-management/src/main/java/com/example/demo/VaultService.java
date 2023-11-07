package com.example.demo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class VaultService {

	@GetMapping("/get-secret")
	public String retrieveSecret() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Vault-Token","hvs.aNBBw0Pk1NVBW34E0x0M6YnG");
		
		String apiUrl = "http://10.63.33.75:8200/v1/secret/data/my";
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(apiUrl,HttpMethod.GET, null, String.class, headers);
		
		System.out.print(response);
		
		return "null";
		
		
		
	}
}
