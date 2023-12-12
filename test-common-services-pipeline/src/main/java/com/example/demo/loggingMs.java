package com.example.demo;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/logging")
public class loggingMs {

	@Value("${jenkinsurl}")
	private String jenkinsurl;
	
	@Value("${username}")
	private String username;
	
	@Value("${password}")
	private String password;
	
	@Value("${jenkinstoken}")
	private String token;
	
	boolean responsecode = false;

	@PostMapping("/deploy-efk")
	public String triggerPipeline(JsonNode body) throws JsonMappingException, JsonProcessingException {
		
		String authStr = username + ":" + password;
		String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());

		RestTemplate resttemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setBasicAuth(base64Creds);
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();


		requestBody.add("Namespace", "efk");
		requestBody.add("Microservices", "Yes");
		requestBody.add("Tools", "Yes");
		requestBody.add("gitlab_ms_branch", "efk-logging");
		requestBody.add("gitlab_tool_branch", "efk");
		

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
		String Jenkinsurl = jenkinsurl + "/job/cluster_microservices1" +  "/buildWithParameters?token=" + token;
		System.out.println(Jenkinsurl);
		ResponseEntity<JsonNode> response = resttemplate.postForEntity(Jenkinsurl,new HttpEntity<>(requestBody, headers), JsonNode.class);
		responsecode = response.getStatusCode().is2xxSuccessful();
		if (responsecode) {
			System.out.println("Job triggered successfully");
			return "The job" + body.get("job").toString() + "has been triggered";
		}

		else {
			System.out.println("Job has not been triggered");
			return "The job  has not been triggered";
		}

	}
}