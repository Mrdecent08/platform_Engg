package com.platformengneering.nexus.service;

import java.util.ArrayList;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@org.springframework.stereotype.Service
public class Service {
	@Autowired
	private RestTemplate restTemplate;
	
	JSONObject json=new JSONObject();
public Object createUser(String token,JsonNode body) throws JsonMappingException, JsonProcessingException {
	json.clear();
	HttpHeaders header = new HttpHeaders();
	

     // Encode the credentials to Base64
     //String credentials = username + ":" + password;
     //String encodedCredentials = java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
     header.set("Authorization", token);
	JSONObject jsonObj = new JSONObject();
	jsonObj.put("userId", body.get("userId").asText());
	jsonObj.put("firstName", body.get("firstName").asText());
	jsonObj.put("lastName", body.get("lastName").asText());
	jsonObj.put("emailAddress", body.get("emailAddress").asText());
	jsonObj.put("password", body.get("password").asText());
	jsonObj.put("status", body.get("status").asText());
	List<String> list = new ArrayList<String>();

		/*
		 * ObjectMapper mapper = new ObjectMapper(); JsonNode node =
		 * mapper.readTree(body.get("roles").asText());
		 * 
		 * for (JsonNode j : node) { list.add(j.asText());
		 * 
		 * }
		 */
	jsonObj.put("roles", body.get("roles"));
	
	ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.34.162:8081/service/rest/v1/security/users", HttpMethod.POST,
			new HttpEntity<>(jsonObj, header),Object.class);
if(entity.getStatusCode().is2xxSuccessful()) {
	json.put("context", "create a user in nexus");
	json.put("status", "success");
	json.put("message", "user created successfully");
}
else {
	json.put("context", "create a user in nexus");
	json.put("status", "failed");
	json.put("message", "unable to create user");
}
return json;
}

public Object deleteUser(String token,String userId) {
	json.clear();
	HttpHeaders header = new HttpHeaders();

     // Encode the credentials to Base64
	 header.set("Authorization", token);
	JSONObject jsonObj = new JSONObject();
	
	
	ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.34.162:8081/service/rest/v1/security/users/"+userId, HttpMethod.DELETE,
			new HttpEntity<>(jsonObj, header),Object.class);
if(entity.getStatusCode().is2xxSuccessful()) {
	json.put("context", "create a user in nexus");
	json.put("status", "success");
	json.put("message", "user deleeted successfully");
}
else {
	json.put("context", "create a user in nexus");
	json.put("status", "failed");
	json.put("message", "unable to delete user");
}
return json;
}

public Object changePassword(String token,String userId,String password) {
	json.clear();
	HttpHeaders header = new HttpHeaders();
	

     // Encode the credentials to Base64
    
     header.set("Authorization",token);


	
	ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.34.162:8081/service/rest/v1/security/users/"+userId+"/change-password", HttpMethod.PUT,
			new HttpEntity<>(password, header),Object.class);
if(entity.getStatusCode().is2xxSuccessful()) {
	json.put("context", "change password in nexus");
	json.put("status", "success");
	json.put("message", "password changed successfully");
}
else {
	json.put("context", "change password in nexus");
	json.put("status", "failed");
	json.put("message", "unable to change password");
}
return json;
}
public Object updateUser(String token,String userId,JsonNode body) {
	json.clear();
	
	HttpHeaders header = new HttpHeaders();
	 

     // Encode the credentials to Base64
	 header.set("Authorization", token);
	JSONObject jsonObj = new JSONObject();
	jsonObj.put("userId", userId);
	jsonObj.put("firstName", body.get("firstName").asText());
	jsonObj.put("lastName", body.get("lastName").asText());
	jsonObj.put("emailAddress", body.get("emailAddress").asText());
	//v1/security/user-sources get source from here
	jsonObj.put("source", body.get("source").asText());
	jsonObj.put("status", body.get("status").asText());
	jsonObj.put("roles", body.get("roles"));
	jsonObj.put("readOnly", body.get("readOnly").asText());
	jsonObj.put("externalRole", body.get("externalRoles"));
	ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.34.162:8081/service/rest/v1/security/users/"+userId, HttpMethod.PUT,
			new HttpEntity<>(jsonObj, header),Object.class);
if(entity.getStatusCode().is2xxSuccessful()) {
	json.put("context", "update a user in nexus");
	json.put("status", "success");
	json.put("message", "user updated successfully");
}
else {
	json.put("context", "update a user in nexus");
	json.put("status", "failed");
	json.put("message", "unable to update user");
}
return json;
}

public Object createRole(String token,JsonNode body) {
	json.clear();
	
	HttpHeaders header = new HttpHeaders();
	 
String Id=body.get("id").asText();
     // Encode the credentials to Base64
header.set("Authorization", token);				
	JSONObject jsonObj = new JSONObject();
	jsonObj.put("id", body.get("id").asText());
	jsonObj.put("name", body.get("name").asText());
	
	jsonObj.put("description", body.get("description").asText());
	jsonObj.put("privileges", body.get("privileges"));
	
	jsonObj.put("roles", body.get("roles"));

	ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.34.162:8081/service/rest/v1/security/roles", HttpMethod.POST,
			new HttpEntity<>(jsonObj, header),Object.class);
if(entity.getStatusCode().is2xxSuccessful()) {
	json.put("context", "create a role in nexus");
	json.put("status", "success");
	json.put("message", "role created successfully");
}
else {
	json.put("context", "create a role in sonarqube");
	json.put("status", "failed");
	json.put("message", "unable to create role");
}
return json;
}
public Object deleteRole(String token,String Id) {
	json.clear();
	HttpHeaders header = new HttpHeaders();


     // Encode the credentials to Base64
	 header.set("Authorization", token);
	
	
	
	ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.34.162:8081/service/rest/v1/security/roles/"+Id, HttpMethod.DELETE,
			new HttpEntity<>( header),Object.class);
if(entity.getStatusCode().is2xxSuccessful()) {
	json.put("context", "delete a role in nexus");
	json.put("status", "success");
	json.put("message", "role deleted successfully");
}
else {
	json.put("context", "delete a role in  nexus");
	json.put("status", "failed");
	json.put("message", "unable to delete role");
}
return json;
}
public Object updateRole(String token,JsonNode body) {
	json.clear();
	String Id=body.get("id").asText();
	HttpHeaders header = new HttpHeaders();
	
     // Encode the credentials to Base64
	 header.set("Authorization", token);
	JSONObject jsonObj = new JSONObject();
	jsonObj.put("id",Id);
	jsonObj.put("name", body.get("name").asText());
	jsonObj.put("description", body.get("description").asText());
	jsonObj.put("privileges", body.get("privileges"));
	jsonObj.put("roles", body.get("roles"));

	ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.34.162:8081/service/rest/v1/security/roles/"+Id, HttpMethod.PUT,
			new HttpEntity<>(jsonObj, header),Object.class);
if(entity.getStatusCode().is2xxSuccessful()) {
	json.put("context", "update a role in nexus");
	json.put("status", "success");
	json.put("message", "role updated successfully");
}
else {
	json.put("context", "update a role in nexus");
	json.put("status", "failed");
	json.put("message", "unable to update role ");
}
return json;
}
public Object createPrivilaege(String token,JsonNode body) {
	json.clear();
	
	String type=body.get("type").asText();
	HttpHeaders header = new HttpHeaders();
	
  String url="http://10.63.34.162:8081/service/rest/v1/security/privileges/"+type;
     // Encode the credentials to Base64
  header.set("Authorization", token);
	JSONObject jsonObj = new JSONObject();
	if(type.equals("application")) {
		jsonObj.put("name", body.get("name").asText());
		jsonObj.put("description", body.get("description").asText());
		jsonObj.put("actions", body.get("actions"));
		jsonObj.put("domain", body.get("domain").asText());
		
	}
	else if(type=="wildcard") {
		jsonObj.put("name", body.get("name").asText());
		jsonObj.put("description", body.get("description").asText());
		jsonObj.put("pattern", body.get("pattern").asText());
	}
	else if (type=="repository-content-selector") {
		jsonObj.put("name", body.get("name").asText());
		jsonObj.put("description", body.get("description").asText());
		jsonObj.put("actions", body.get("actions"));
		jsonObj.put("format", body.get("format").asText());
		jsonObj.put("repository", body.get("repository").asText());
		jsonObj.put("contentSelector", body.get("contentSelector").asText());
	}
	else if(type=="repository-admin") {
		jsonObj.put("name", body.get("name").asText());
		jsonObj.put("description", body.get("description").asText());
		jsonObj.put("actions", body.get("actions"));
		jsonObj.put("format", body.get("format").asText());
		jsonObj.put("repository", body.get("repository").asText());
		
	}
	else if(type=="repository-view") {
		jsonObj.put("name", body.get("name").asText());
		jsonObj.put("description", body.get("description").asText());
		jsonObj.put("actions", body.get("actions"));
		jsonObj.put("format", body.get("format").asText());
		jsonObj.put("repository", body.get("repository").asText());
		
	}
	ResponseEntity<Object> entity=restTemplate.exchange(url, HttpMethod.POST,
			new HttpEntity<>(jsonObj, header),Object.class);
if(entity.getStatusCode().is2xxSuccessful()) {
	json.put("context", "create a Privilage in nexus");
	json.put("status", "success");
	json.put("message", "Privilage created  successfully");
}
else {
	json.put("context", "create a Privilage in nexus");
	json.put("status", "failed");
	json.put("message", "unable to create Privilage");
}
return json;
}
public Object deleteprivilege(String token,String privilegename) {
	json.clear();
	HttpHeaders header = new HttpHeaders();

     // Encode the credentials to Base64
	 header.set("Authorization", token);
	
	
	
	ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.34.162:8081/service/rest/v1/security/privileges/"+privilegename, HttpMethod.DELETE,
			new HttpEntity<>( header),Object.class);
if(entity.getStatusCode().is2xxSuccessful()) {
	json.put("context", "delete a Privilage in nexus");
	json.put("status", "success");
	json.put("message", "Privilage created successfully");
}
else {
	json.put("context", "delete a Privilage in nexus");
	json.put("status", "failed");
	json.put("message", "unable to delete Privilage");
}
return json;
}
public Object createazureblobStorage(String token,JsonNode body) {
	json.clear();
	
	HttpHeaders header = new HttpHeaders();

String Id=body.get("Id").asText();
     // Encode the credentials to Base64
header.set("Authorization", token);
     
	JSONObject jsonObj = new JSONObject();
	
	jsonObj.put("name", body.get("name").asText());
	jsonObj.put("softQuota", body.get("softQuota"));
	jsonObj.put("bucketConfiguration", body.get("bucketConfiguration"));
	
		

	ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.34.162:8081/service/rest/v1/blobstores/azure/"+Id, HttpMethod.POST,
			new HttpEntity<>(jsonObj, header),Object.class);
if(entity.getStatusCode().is2xxSuccessful()) {
	json.put("context", "create a azureblob in nexus");
	json.put("status", "success");
	json.put("message", "azureblob successfully");
}
else {
	json.put("context", "create a azureblob in nexus");
	json.put("status", "failed");
	json.put("message", "unable to create azureblob");
}
return json;
}
public Object deleteblobstores(String token,String name) {
	json.clear();
	HttpHeaders header = new HttpHeaders();
	

     // Encode the credentials to Base64
	 header.set("Authorization", token);

	
	
	ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.34.162:8081/service/rest/v1/blobstores/"+name, HttpMethod.DELETE,
			new HttpEntity<>( header),Object.class);
if(entity.getStatusCode().is2xxSuccessful()) {
	json.put("context", "delete blob stores in nexus");
	json.put("status", "success");
	json.put("message", "blob stores deleted successfully");
}
else {
	json.put("context", "delete blob stores in nexus");
	json.put("status", "failed");
	json.put("message", "unable to delete blob store");
}
return json;
}
public Object creates3blobStorage(String token,JsonNode body) {
	json.clear();
	
	HttpHeaders header = new HttpHeaders();
	 

     // Encode the credentials to Base64
header.set("Authorization", token);
	JSONObject jsonObj = new JSONObject();
	//NEED TO CNAE IT 
	jsonObj.put("name", body.get("name").asText());
	jsonObj.put("softQuota", body.get("softQuota"));
	jsonObj.put("bucketConfiguration", body.get("bucketConfiguration"));
	
		

	ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.34.162:8081/service/rest/v1/blobstores/s3", HttpMethod.POST,
			new HttpEntity<>(jsonObj, header),Object.class);
if(entity.getStatusCode().is2xxSuccessful()) {
	json.put("context", "create a s3 blobstores in nexus");
	json.put("status", "success");
	json.put("message", "s3 blobstores created successfully");
}
else {
	json.put("context", "create a s3 blobstores in nexus");
	json.put("status", "failed");
	json.put("message", "unable to create s3 blobstores");
}
return json;
}
public Object createContentSelector(String token,JsonNode body) {
	json.clear();
	
	HttpHeaders header = new HttpHeaders();
	

     // Encode the credentials to Base64
header.set("Authorization", token);
     
	JSONObject jsonObj = new JSONObject();
	//NEED TO CNAE IT 
	jsonObj.put("name", body.get("name").asText());
	jsonObj.put("description", body.get("description").asText());
	jsonObj.put("expression", body.get("expression").asText());
	
		

	ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.34.162:8081/service/rest/v1/security/content-selectors/", HttpMethod.POST,
			new HttpEntity<>(jsonObj, header),Object.class);
if(entity.getStatusCode().is2xxSuccessful()) {
	json.put("context", "create a contentSelector in nexus");
	json.put("status", "success");
	json.put("message", "contentSelector created successfully");
}
else {
	json.put("context", "create a contentSelector in nexus");
	json.put("status", "failed");
	json.put("message", "unable to create  contentSelector");
}
return json;
}
public Object deleteContentSelector(String token,String name) {
	json.clear();
	HttpHeaders header = new HttpHeaders();
	

     // Encode the credentials to Base64
	 header.set("Authorization", token);
	
	
	
	ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.34.162:8081/service/rest/v1/security/content-selectors/"+name, HttpMethod.DELETE,
			new HttpEntity<>( header),Object.class);
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

public Object createRoutingRules(String token,JsonNode body) {
	json.clear();
	
	HttpHeaders header = new HttpHeaders();
	

     // Encode the credentials to Base64
	 header.set("Authorization", token);
	JSONObject jsonObj = new JSONObject();
	//NEED TO CNAE IT 
	jsonObj.put("name", body.get("name").asText());
	jsonObj.put("description", body.get("description").asText());
	jsonObj.put("mode", body.get("mode").asText());
	jsonObj.put("matchers", body.get("matchers"));
		

	ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.34.162:8081/service/rest/v1/routing-rules", HttpMethod.POST,
			new HttpEntity<>(jsonObj, header),Object.class);
if(entity.getStatusCode().is2xxSuccessful()) {
	json.put("context", "create RoutingRules in nexus");
	json.put("status", "success");
	json.put("message", "routingRules created successfully");
}
else {
	json.put("context", "create RoutingRules in nexus");
	json.put("status", "failed");
	json.put("message", "unable to create routingRules");
}
return json;
}
public Object deleteRoutingRules(String token,String name) {
	json.clear();
	HttpHeaders header = new HttpHeaders();
	

     // Encode the credentials to Base64
	 header.set("Authorization", token);
	
	
	ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.34.162:8081/service/rest/v1/routing-rules/"+name, HttpMethod.DELETE,
			new HttpEntity<>( header),Object.class);
if(entity.getStatusCode().is2xxSuccessful()) {
	json.put("context", "delete  routingRules in nexus");
	json.put("status", "success");
	json.put("message", "routingRule deleted successfully");
}
else {
	json.put("context", "delete  routingRules in nexus");
	json.put("status", "failed");
	json.put("message", "unable to delete routingRule");
}
return json;
}
public Object updateAnonymousAccess(String token,JsonNode body) {
	json.clear();
	HttpHeaders header = new HttpHeaders();


     // Encode the credentials to Base64
	 header.set("Authorization", token);
	JSONObject jsonObj = new JSONObject();
	
	jsonObj.put("enabled", body.get("enabled").asText());
	jsonObj.put("userId", body.get("userId").asText());
	jsonObj.put("realmName", body.get("realmName").asText());

	ResponseEntity<Object> entity=restTemplate.exchange("http://10.63.34.162:8081/service/rest/v1/security/anonymous", HttpMethod.PUT,
			new HttpEntity<>(jsonObj, header),Object.class);
if(entity.getStatusCode().is2xxSuccessful()) {
	json.put("context", "update anonymous access  in nexus");
	json.put("status", "success");
	json.put("message", "anonymous access updated successfully");
}
else {
	json.put("context", "update anonymous access  in nexus");
	json.put("status", "failed");
	json.put("message", "unable to update anonymous access");
}
return json;
}
}
