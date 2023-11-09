package com.example.demo;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class VaultService {

	@GetMapping("/get-secret")
	public JsonNode retrieveSecret(@RequestHeader("vault-token") String vault_token,@RequestParam("application_name") String appName) throws IOException {
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Vault-Token",vault_token);
		
		// String apiUrl = "http://10.63.33.75:8200/v1/secret/data/"+appName;
		
		String apiUrl = "http://10.63.20.59:31445/v1/secret/data/"+appName;
		
		RestTemplate restTemplate = new RestTemplate();
		
		ObjectMapper mapper = new ObjectMapper();
		
		ResponseEntity<String> response = restTemplate.exchange(apiUrl,HttpMethod.GET, new HttpEntity<>(headers), String.class);
		
		JsonNode dataNode = mapper.readTree(response.getBody().toString());

		System.out.print(dataNode.get("data").get("data"));
		
		return dataNode.get("data").get("data");
		
	}
	
	@PostMapping("/add-secret")
	public String addSecret(@RequestHeader("vault-token") String vault_token,@RequestBody Credentials credentials) throws JsonMappingException, JsonProcessingException {

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Vault-Token",vault_token);
		
		// String apiUrl = "http://10.63.33.75:8200/v1/secret/data/"+credentials.getApplicationName();
		
		String apiUrl = "http://10.63.20.59:31445/v1/secret/data/"+credentials.getApplicationName();
		
		RestTemplate restTemplate = new RestTemplate();
		
		String jsonPayload = String.format("{\"data\": {\"%s\": \"%s\",\"%s\":\"%s\"}}","username",credentials.getUsername(),"password",credentials.getPassword());
		
		ResponseEntity<Object> response = restTemplate.exchange(apiUrl,HttpMethod.POST, new HttpEntity<>(jsonPayload,headers), Object.class);

		return "Secret Stored Successfully ";
		
	}
	
	@DeleteMapping("/delete-secret")
	public String deleteSecret(@RequestHeader("vault-token") String vault_token,@RequestParam("application_name") String appName) throws IOException {
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.set("X-Vault-Token",vault_token);
		
		// String apiUrl = "http://10.63.33.75:8200/v1/secret/metadata/"+appName;
		
		String apiUrl = "http://10.63.20.59:31445/v1/secret/metadata/"+appName;
		
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.exchange(apiUrl,HttpMethod.DELETE, new HttpEntity<>(headers), String.class);
		
		System.out.print(response.getBody());
		
		return "Secret Deleted Successfully";
		
	}
	
	
}
