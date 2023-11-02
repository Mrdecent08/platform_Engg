package com.sonar.sonarqube.service;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

@Component
public class Service {
	@Autowired
	private RestTemplate restTemplate;
	JSONObject json=new JSONObject();
	public Object createUser(JsonNode body, String token) {
		json.clear();
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);

		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("login", body.get("login").asText());
		formData.add("name", body.get("name").asText());
		formData.add("password", body.get("password").asText());
		ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.35.244:32155/api/users/create", HttpMethod.POST,
				new HttpEntity<>(formData, header),Object.class);
	if(entity.getStatusCode().is2xxSuccessful()) {
		json.put("context", "create a user in sonarqube");
		json.put("status", "success");
		json.put("message", "user created successfully");
	}
	else {
		json.put("context", "create a user in sonarqube");
		json.put("status", "failed");
		json.put("message", "unable to create user");
	}
	return json;
	}

	public Object createProject(JsonNode body, String token) {
		json.clear();
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);
		// header.add("Authorization", "Bearer "+token);
		// header.
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("project", body.get("key").asText());
		formData.add("name", body.get("name").asText());
		formData.add("visibility", body.get("visibility").asText());
		ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.35.244:32155/api/projects/create", HttpMethod.POST,
				new HttpEntity<>(formData, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "create a project in sonarqube");
			json.put("status", "success");
			json.put("message", "project created");
		}
		else {
			json.put("context", "create a project in sonarqube");
			json.put("status", "failed");
			json.put("message", "unable to create project");
		}
		
		return json;
	}

	public Object createQualityProfile(JsonNode body, String token) {
		json.clear();
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);
		// header.add("Authorization", "Bearer "+token);
		// header.
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("language", body.get("language").asText());
		formData.add("name", body.get("name").asText());
	//	formData.add("backupSonarWay", body.get("backupSonarWay").asText());
		ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.35.244:32155/api/qualityprofiles/create", HttpMethod.POST,
				new HttpEntity<>(formData, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "create a quality profile in sonarqube");
			json.put("status", "success");
			json.put("message", "quality profile created");
		}
		else {
			json.put("context", "create a quality profile in sonarqube");
			json.put("status", "failed");
			json.put("message", "unable to create quality profile");
		}
		return json;
	}

	public Object deactivateUser(JsonNode body, String token) {
		json.clear();
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);
		// header.add("Authorization", "Bearer "+token);
		// header.
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("login", body.get("login").asText());

		ResponseEntity<Object> entity=	restTemplate.exchange("http://10.63.35.244:32155/api/users/deactivate", HttpMethod.POST,
				new HttpEntity<>(formData, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "deactivate a user in sonarqube");
			json.put("status", "success");
			json.put("message", "user deactivates");
		}
		else {
			json.put("context", "deactivate a user in sonarqube");
			json.put("status", "failed");
			json.put("message", "unable to deactivate a user");
		}
		return json;
	}

	public Object addUser(JsonNode body, String token) {
		json.clear();
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);
		// header.add("Authorization", "Bearer "+token);
		// header.
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("login", body.get("login").asText());// username
		formData.add("name", body.get("name").asText());// groupname

		ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.35.244:32155/api/user_groups/add_user", HttpMethod.POST,
				new HttpEntity<>(formData, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "add a user to a group in sonarqube");
			json.put("status", "success");
			json.put("message", "sucessfully added  a user to a group");
		}
		else {
			json.put("context", "add a user to a group in sonarqube");
			json.put("status", "failed");
			json.put("message", "unable to add a user to a group");
		}
		
		return json;
	}

	public Object deleteGroup(JsonNode body, String token) {
		json.clear();
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);
		// header.add("Authorization", "Bearer "+token);
		// header.
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("name", body.get("name").asText());

		ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.35.244:32155/api/user_groups/delete", HttpMethod.POST,
				new HttpEntity<>(formData, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "delete a group in sonarqube");
			json.put("status", "success");
			json.put("message", "successfully deleted group");
		}
		else {
			json.put("context", "delete a group in sonarqube");
			json.put("status", "failed");
			json.put("message", "unable to delete group");
		}
		return json;
		
	}

	public Object removeUser(JsonNode body, String token) {
		json.clear();
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);
		// header.add("Authorization", "Bearer "+token);
		// header.
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("name", body.get("name").asText());
		formData.add("login", body.get("login").asText());

		ResponseEntity<Object> entity=	restTemplate.exchange("http://10.63.35.244:32155/api/user_groups/remove_user", HttpMethod.POST,
				new HttpEntity<>(formData, header), Object.class);if(entity.getStatusCode().is2xxSuccessful()) {
					json.put("context", "remove a user from a group");
					json.put("status", "success");
					json.put("message", "Successfully removed user from group");
				}
				else {
					json.put("context", "remove a user from a group");
					json.put("status", "failed");
					json.put("message", "unable to remove user from group");
				}
				return json;
	}


	public Object addProject(JsonNode body, String token) {
		json.clear();
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);
		// header.add("Authorization", "Bearer "+token);
		// header.
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("language", body.get("language").asText());
		formData.add("project", body.get("project").asText());
		formData.add("qualityProfile", body.get("qualityProfile").asText());
		ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.35.244:32155/api/qualityprofiles/add_project", HttpMethod.POST,
				new HttpEntity<>(formData, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "add a project to quality profile");
			json.put("status", "success");
			json.put("message", "successfully added project to quality profile");
		}
		else {
			json.put("context", "add a project to quality profile");
			json.put("status", "failed");
			json.put("message", "unable to add a project to qualityprofile");
		}
		
		return json;
	}

	public Object changeParent(JsonNode body, String token) {
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);
		// header.add("Authorization", "Bearer "+token);
		// header.
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("language", body.get("language").asText());
		formData.add("qualityProfile", body.get("qualityProfile").asText());
		formData.add("parentQualityProfile", body.get("parentQualityProfile").asText());
		ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.35.244:32155/api/qualityprofiles/change_parent", HttpMethod.POST,
				new HttpEntity<>(formData, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "change parent in quality profile");
			json.put("status", "success");
			json.put("message", "successfully changed parent in quality profile");
		}
		else {
			json.put("context", "change parent in quality profile");
			json.put("status", "failed");
			json.put("message", "unable to change parent in quality profile");
		}
		return json;
	}

	public Object addGroupPermission(JsonNode body, String token) {
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);
		// header.add("Authorization", "Bearer "+token);
		// header.
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("groupName", body.get("groupName").asText());
		formData.add("permission", body.get("permission").asText());

	ResponseEntity<Object>entity=	restTemplate.exchange("http://10.63.35.244:32155/api/permissions/add_group", HttpMethod.POST,
				new HttpEntity<>(formData, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
					json.put("context", "add grouppermission in sonarqube");
					json.put("status", "success");
					json.put("message", "successfully added group permission");
				}
				else {
					json.put("context", "add grouppermission in sonarqube");
					json.put("status", "failed");
					json.put("message", "unable to add group permission");
				}
		return json;
	}
	

	public Object changeUserPermission(JsonNode body, String token) {
		json.clear();
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);
		// header.add("Authorization", "Bearer "+token);
		// header.
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("login", body.get("login").asText());
		formData.add("permission", body.get("permission").asText());

		ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.35.244:32155/api/permissions/add_user", HttpMethod.POST,
				new HttpEntity<>(formData, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "add user permission in sonarqube");
			json.put("status", "success");
			json.put("message", "successfully changed user permission");
		}
		else {
			json.put("context", "add grouppermission in sonarqube");
			json.put("status", "failed");
			json.put("message", "unable to add user permission");
		}
		return json;
	}

	public Object removeGroupPermission(JsonNode body, String token) {
		json.clear();
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);
		// header.add("Authorization", "Bearer "+token);
		// header.
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("groupName", body.get("groupName").asText());
		formData.add("permission", body.get("permission").asText());

		ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.35.244:32155/api/permissions/remove_group", HttpMethod.POST,
				new HttpEntity<>(formData, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "remove group permission in sonaqube");
			json.put("status", "success");
			json.put("message", "sucessfully removed group permission");
		}
		else {
			json.put("context", "remove group permission in sonaqube");
			json.put("status", "failed");
			json.put("message", "unable to remove group permission");
		}
		
		return json;
	}
	public Object removeUserPermission(JsonNode body, String token) {
		json.clear();
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);
		// header.add("Authorization", "Bearer "+token);
		// header.
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("login", body.get("login").asText());
		formData.add("permission", body.get("permission").asText());

		ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.35.244:32155/api/permissions/remove_user", HttpMethod.POST,
				new HttpEntity<>(formData, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "remove user permission in sonaqube");
			json.put("status", "success");
			json.put("message", "sucessfully removed user permission");
		}
		else {
			json.put("context", "remove user permission in sonaqube");
			json.put("status", "failed");
			json.put("message", "unable to remove user permission");
		}
		
		return json;
	}

	public Object updateProjectVisibility(JsonNode body, String token) {
		json.clear();
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);
		// header.add("Authorization", "Bearer "+token);
		// header.
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("project", body.get("project").asText());

		ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.35.244:32155/api/projects/update_visibility", HttpMethod.POST,
				new HttpEntity<>(formData, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "update project visibility in sonarqube");
			json.put("status", "success");
			json.put("message", "Successfully updated visibility of a project");
		}
		else {
			json.put("context", "update project visibility in sonarqube");
			json.put("status", "failed");
			json.put("message", "unable to update project visibility");
		}
		return json;
	}
	public Object createWebhook(JsonNode body,String token) {
		json.clear();
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);
		// header.add("Authorization", "Bearer "+token);
		// header.
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("name", body.get("name").asText());
       formData.add("url", body.get("url").asText());
		ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.35.244:32155/api/webhooks/create", HttpMethod.POST,
				new HttpEntity<>(formData, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "create a webhook");
			json.put("status", "success");
			json.put("message", "Successfully created webhook");
		}
		else {
			json.put("context", "create a webhook");
			json.put("status", "failed");
			json.put("message", "unable to create webhook");
		}
		return json;
		
	}
	public Object deleteWebhook(JsonNode body,String token) {
		json.clear();
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);
		// header.add("Authorization", "Bearer "+token);
		// header.
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("webhook", body.get("webhook").asText());
      
		ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.35.244:32155/api/webhooks/delete", HttpMethod.POST,
				new HttpEntity<>(formData, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "delete a webhook");
			json.put("status", "success");
			json.put("message", "Successfully deleted webhook");
		}
		else {
			json.put("context", "delete a webhook");
			json.put("status", "failed");
			json.put("message", "unable to delete webhook");
		}
		return json;
		
	}
	
	public Object createToken(JsonNode body,String token) {
		json.clear();
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);
		// header.add("Authorization", "Bearer "+token);
		// header.
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("name", body.get("name").asText());
      
		ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.35.244:32155/api/user_tokens/generate", HttpMethod.POST,
				new HttpEntity<>(formData, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "create a token");
			json.put("status", "success");
			json.put("message", "Successfully created a token");
		}
		else {
			json.put("context", "create a token");
			json.put("status", "failed");
			json.put("message", "unable to create a token");
		}
		return json;
		
	}


	public Object revokeToken(JsonNode body,String token) {
		json.clear();
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);
		// header.add("Authorization", "Bearer "+token);
		// header.
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("name", body.get("name").asText());
      
		ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.35.244:32155/api/user_tokens/revoke", HttpMethod.POST,
				new HttpEntity<>(formData, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "revoke a token");
			json.put("status", "success");
			json.put("message", "Successfully revoked  token");
		}
		else {
			json.put("context", "revoke a token");
			json.put("status", "failed");
			json.put("message", "unable to revoke token");
		}
		return json;
		
	}

		
}
