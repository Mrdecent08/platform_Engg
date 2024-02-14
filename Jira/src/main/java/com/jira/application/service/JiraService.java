package com.jira.application.service;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;


@Service
public class JiraService {
	@Autowired
	private RestTemplate restTemplate;
	  public void createProject(JsonNode body) {
	
			   
		 JSONObject json=new JSONObject();

json.put("assigneeType", body.get("assigneeType").asText());
json.put("description", body.get("description").asText());
json.put("key", body.get("key").asText());
json.put("leadAccountId", body.get("leadAccountId").asText());
json.put("name", body.get("name").asText());
json.put("projectTemplateKey", body.get("projectTemplateKey").asText());
json.put("projectTypeKey", body.get("projectTypeKey").asText());
json.put("url", body.get("url").asText());
		HttpHeaders header=new HttpHeaders();
		header.set("Authorization", "Basic YWJoaXNoZWsudmVsaWNoYWxhQHRjcy5jb206QVRBVFQzeEZmR0YwLVBRamJoaXUtUWdMTUptcUZ0dUhlc2NrZWN6eHUycEo2Y0FsaTk3X3gzWFlWSmQzeHNzNjNtSUFWMVBzOE1aM0lQbi1TU1dwaTMwaDlLbzd1QTNOMmszSEhnMFFIUlltT1dXMnJlOVNONUQ3M0xaV3lxb2dzbGdzUUMwcWQzSTBFTVh4YTRwTXRiZTh1ajB5M0cyc250M0hUZllJUDgwYUVFTWxrU0FBenFjPTcyNTYxNzFE");
			    ResponseEntity<Object> entity=restTemplate.exchange("https://abhishek-v.atlassian.net/rest/api/3/project", HttpMethod.POST, new HttpEntity<>(json, header),Object.class);
				
		    
		  }
	  public void createIssue(JsonNode body) {
		  
		  HttpHeaders header=new HttpHeaders();
		
			header.set("Authorization", "Basic YWJoaXNoZWsudmVsaWNoYWxhQHRjcy5jb206QVRBVFQzeEZmR0YwLVBRamJoaXUtUWdMTUptcUZ0dUhlc2NrZWN6eHUycEo2Y0FsaTk3X3gzWFlWSmQzeHNzNjNtSUFWMVBzOE1aM0lQbi1TU1dwaTMwaDlLbzd1QTNOMmszSEhnMFFIUlltT1dXMnJlOVNONUQ3M0xaV3lxb2dzbGdzUUMwcWQzSTBFTVh4YTRwTXRiZTh1ajB5M0cyc250M0hUZllJUDgwYUVFTWxrU0FBenFjPTcyNTYxNzFE");
		    ResponseEntity<Object> entity=restTemplate.exchange("https://abhishek-v.atlassian.net/rest/api/2/issue/", HttpMethod.POST, new HttpEntity<>(body, header),Object.class);
	  }
	  
	  
	  public void createProjectInGitlab(JsonNode body) {
		  HttpHeaders header = new HttpHeaders();
			header.setBearerAuth("");
			   ResponseEntity<Object> response = restTemplate.exchange("https://gitlab.com/api/v4/projects",HttpMethod.POST,
					   new HttpEntity<>(body, header), Object.class);
	  }
}
