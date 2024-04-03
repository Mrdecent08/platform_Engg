package com.example.demo;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
public class AlertHookApplication {

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

//        @PostMapping("/alert-hook")
//        public void receiveAlertHook() throws Exception {
//                String alertManagerUrl = "http://10.63.20.37:30279/api/v2/alerts/groups";
//                String response = restTemplate.getForEntity(alertManagerUrl, String.class).getBody();
//                ObjectMapper mapper = new ObjectMapper();
//                JsonNode jsonNode = mapper.readTree(response);
//                List<JsonNode> alertList = new ArrayList<>();
//                if (jsonNode.isArray()) {
//                        for (JsonNode node : jsonNode) {
//                                alertList.add(node);
//                        }
//                }
//                alertList.sort(Comparator.comparing(node -> ZonedDateTime.parse(node.get("startsAt").asText())));
//                ArrayNode sortedArrayNode = mapper.createArrayNode();
//                for (JsonNode node : alertList) {
//                        sortedArrayNode.add(node);
//                }
//                System.out.println("----------------------------------------------------------------------");
//                System.out.println(sortedArrayNode.get(0).get("alerts"));
//                System.out.println("-----------------------------------------------------------------------");
//                System.out.println(sortedArrayNode.get(0).get("alerts").size());
//                System.out.println("-----------------------------------------------------------------------");
//                System.out.println(sortedArrayNode.get(0).get("alerts").get(sortedArrayNode.get(0).get("alerts").size() - 1));
//        }

	@PostMapping("/alert-hook")
	public void receiveAlertHook() throws Exception {
		String alertManagerUrl = "http://10.63.20.37:30279/api/v2/alerts/groups";
		String response = restTemplate.getForEntity(alertManagerUrl, String.class).getBody();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(response);
		for (int i = 0; i < jsonNode.get(0).get("alerts").size(); i++) {
			String alertName = jsonNode.get(0).get("alerts").get(i).get("labels").get("alertname").asText();
			String podName = jsonNode.get(0).get("alerts").get(i).get("labels").get("pod").asText();
			String namespace = jsonNode.get(0).get("alerts").get(i).get("labels").get("namespace").asText();
			System.out.println("-------------------------------------------");
			System.out.println(alertName+" --> "+ podName+" --> "+ namespace);
			triggerPipeline(alertName,podName,namespace);
		}
		System.out.println("*******************************************************");
	}

	public String triggerPipeline(String alertName, String podName, String namespace) throws JsonMappingException, JsonProcessingException {

		String authStr = username + ":" + password;
		String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setBasicAuth(base64Creds);
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();

		requestBody.add("alertName", alertName);
		requestBody.add("podName", podName);
		requestBody.add("namespace", namespace);

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
		String Jenkinsurl = jenkinsurl + "/job/auto-healing" + "/buildWithParameters?token=" + token;
		System.out.println(Jenkinsurl);
		ResponseEntity<JsonNode> response = restTemplate.postForEntity(Jenkinsurl,
				new HttpEntity<>(requestBody, headers), JsonNode.class);
		responsecode = response.getStatusCode().is2xxSuccessful();
		if (responsecode) {
			System.out.println("Job triggered successfully");
			return "The job has been triggered";
		}

		else {
			System.out.println("Job has not been triggered");
			return "The job  has not been triggered";
		}

	}
	
	public static void main(String[] args) {
		SpringApplication.run(AlertHookApplication.class, args);
	}

}