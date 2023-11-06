package com.platformengneering.pipelin;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

public class Service {
	@Value("${jenkinsurl}")
	private String jenkinsurl;
	@Value("${username}")
	private String username;
	@Value("${password}")
	private String password;
	@Value("${jenkinstoken}")
	private String token;
	boolean responsecode = false;
	public String triggerPipeline(JsonNode body) {
		String authStr = username + ":" + password;
		String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());

		RestTemplate resttemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		HttpEntity requestEntity = new HttpEntity<>(headers);
		String Jenkinsurl = jenkinsurl + "/job/" + body.get("job").toString();
		ResponseEntity<JsonNode> response = resttemplate.postForEntity(Jenkinsurl, new HttpEntity<>(headers),
				JsonNode.class);
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
