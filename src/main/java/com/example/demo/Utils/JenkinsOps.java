package com.example.demo.Utils;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@RestController
public class JenkinsOps {

	@Value("${jenkinsurl}")
	private String jenkinsurl;

	@Value("${username}")
	private String username;

	@Value("${password}")
	private String password;

	@Value("${jenkinstoken}")
	private String token;

	boolean responsecode = false;

	RestTemplate restTemplate = new RestTemplate();

	@PostMapping("/createEnv")
	public  ResponseEntity<String> triggerPipeline() throws IOException,JsonMappingException, JsonProcessingException {

		String authStr = username + ":" + password;
		String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setBasicAuth(base64Creds);
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
		String Jenkinsurl = jenkinsurl + "/job/kor" + "/buildWithParameters?token=" + token;
		ResponseEntity<JsonNode> response = restTemplate.postForEntity(Jenkinsurl,
				new HttpEntity<>(requestBody, headers), JsonNode.class);
		String locationHeader = response.getHeaders().getFirst("Location");
		String authHeaderValue = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
		String buildNumber = RetrieveBuildNumber(locationHeader, authHeaderValue);
		responsecode = response.getStatusCode().is2xxSuccessful();
		if (response.getStatusCode() == HttpStatus.CREATED) {
			System.out.println("Job triggered successfully");
			return new ResponseEntity<>(buildNumber,HttpStatus.OK);
		}

		else {
			System.out.println("Job has not been triggered");
			 return new ResponseEntity<>("Failed to trigger Jenkins job", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public String RetrieveBuildNumber(String jobQueueUrl, String authHeaderValue) {
		try {
			int maxAttempts = 10;
			int pollingIntervalSeconds = 10;

			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", authHeaderValue);

			for (int attempt = 1; attempt <= maxAttempts; attempt++) {
				String apiUrl = jobQueueUrl + "api/json";

				RestTemplate restTemplate = new RestTemplate();
				HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(headers);
				ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity,
						String.class);

				if (responseEntity.getStatusCode() == HttpStatus.OK) {
					String jsonResponse = responseEntity.getBody();
					String buildNumber = extractBuildNumberFromJson(jsonResponse);

					if (buildNumber != null) {
						return buildNumber;
					}
				}

				Thread.sleep(pollingIntervalSeconds * 1000);
			}

			return null;
		} catch (Exception e) {
			System.out.print("exception: " + e);
			return null;
		}
	}

	private String extractBuildNumberFromJson(String jsonResponse) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(jsonResponse);
			if (jsonNode.has("executable")) {
				JsonNode executableNode = jsonNode.get("executable");
				if (executableNode.has("number")) {
					int buildNumber = executableNode.get("number").asInt();
					return String.valueOf(buildNumber);
				}
			}

			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(JenkinsOps.class, args);
	}

}
