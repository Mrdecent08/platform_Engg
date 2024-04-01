package com.example.demo;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@SpringBootApplication
@RestController
public class AlertHookApplication {

	RestTemplate restTemplate = new RestTemplate();

	@PostMapping("/alert-hook")
	public void receiveAlertHook() throws Exception {
		String alertManagerUrl = "http://10.63.20.37:30279/api/v2/alerts/groups";
		String response = restTemplate.getForEntity(alertManagerUrl, String.class).getBody();
		System.out.println(response.toString());
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(response);
		List<JsonNode> alertList = new ArrayList<>();
		if (jsonNode.isArray()) {
			for (JsonNode node : jsonNode) {
				alertList.add(node);
			}
		}
		alertList.sort(Comparator.comparing(node -> ZonedDateTime.parse(node.get("startsAt").asText())));
		ArrayNode sortedArrayNode = mapper.createArrayNode();
		for (JsonNode node : alertList) {
			sortedArrayNode.add(node);
		}
		System.out.println(sortedArrayNode.get("alerts"));
		System.out.println("-----------------------------------------------------------------------");
		System.out.println(sortedArrayNode.get("alerts").size());
		System.out.println("-----------------------------------------------------------------------");
		System.out.println(sortedArrayNode.get("alerts").get(sortedArrayNode.get("alerts").size() - 1));
	}

	public static void main(String[] args) {
		SpringApplication.run(AlertHookApplication.class, args);
	}

}
