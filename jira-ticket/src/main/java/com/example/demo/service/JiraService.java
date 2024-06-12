package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.JiraTicket;

@Service
public class JiraService {

	@Value("${jira.url}")
	private String jiraUrl;
	
	@Value("${jira.url}")
	private String jiraUsername;
	
	@Value("${jira.password}")
	private String jiraPassword;
	
	private final RestTemplate restTemplate;

	public JiraService() {
		super();
		this.restTemplate = new RestTemplate();
	}
	
	public String createTicket(JiraTicket ticket) {
		String url = jiraUrl + "/rest/api/2/issue";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBasicAuth(jiraUsername, jiraPassword);
		
		Map<String,Object> fields = new HashMap<>();
		fields.put("project", Map.of("key",ticket.getProjectKey()));
		fields.put("summary", ticket.getSummary());
		fields.put("description",ticket.getDescription());
		fields.put("issuetype", Map.of("name",ticket.getIssueType()));
		
		Map<String,Object> requestBody = new HashMap<>();
		requestBody.put("fields", fields);
		
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return "Ticket created successfully: " + response.getBody();
        } else {
            throw new RuntimeException("Failed to create ticket: " + response.getBody());
        }
        
	}
	
	
}
