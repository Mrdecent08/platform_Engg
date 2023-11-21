package com.platform.git.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Git;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@org.springframework.stereotype.Service
public class Service {

	
	@Autowired
	RestTemplate restTemplate;
	
	
	@Value("${giturl}")
	String giturl;

	
	JSONObject json=new JSONObject();
//create a Repository
//done
	public Object createRepo(JsonNode data, String token) {
		json.clear();
		
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(newtoken);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("name", data.get("name").asText());
		jsonObj.put("private", data.get("name").asText());
		System.out.println(jsonObj);
		
	ResponseEntity<Object> entity=restTemplate.exchange(giturl+"/api/v4/projects", HttpMethod.POST, new HttpEntity<>(jsonObj, header),Object.class);
	if(entity.getStatusCode().is2xxSuccessful()) {
		json.put("context", "create a repo in git");
		json.put("status", "success");
		json.put("message", "repo created successfully");
	}
	else {
		json.put("context", "create a repo in git");
		json.put("status", "failed");
		json.put("message", "unable to create repo");
	}
	return json;
	}

//done
	public ResponseEntity<List> get(String token) {
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(newtoken);
	
		ResponseEntity<List> entity=restTemplate.exchange(giturl+"/api/v4/projects", HttpMethod.GET, new HttpEntity<>(header), List.class);
		
		return entity;
	}

//delete a repository
//done
	public Object deleteRepo(JsonNode body, String token) {
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(newtoken);

		ResponseEntity<Object> entity=restTemplate.exchange(
				giturl+"/api/v4/projects/" + body.get("id").asText(),
				HttpMethod.DELETE, new HttpEntity<>(header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "delete a repo in git");
			json.put("status", "success");
			json.put("message", "repo deleted successfully");
		}
		else {
			json.put("context", "delete a repo in git");
			json.put("status", "failed");
			json.put("message", "unable to delete repo");
		}
		return json;
	}

//create a Team
//done
	
//update visibility of a repository
//done
	public Object updateVisibility(JsonNode body, String token) {
		json.clear();
		HttpHeaders header = new HttpHeaders();
		String newtoken = token.replaceAll("Bearer ", "");
		header.setBearerAuth(newtoken);
		JSONObject json = new JSONObject();
		json.put("visibility", body.get("visibility").asText());
		ResponseEntity<Object> entity=restTemplate.exchange(
				giturl+"/api/v4/projects/" + body.get("id").asText(),
				HttpMethod.PATCH, new HttpEntity<>(json, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "update visibility of a repo ");
			json.put("status", "success");
			json.put("message", "visibility updated successfully");
		}
		else {
			json.put("context", "update visibility of a repo");
			json.put("status", "failed");
			json.put("message", "unable to update visibility");
		}
		return json;
	}
	
	public Object createUser(JsonNode body, String token) {
		json.clear();
		HttpHeaders header = new HttpHeaders();
		String newtoken = token.replaceAll("Bearer ", "");
		header.setBearerAuth(newtoken);
		JSONObject json = new JSONObject();
		json.put("username", body.get("username").asText());
		json.put("email", body.get("email").asText());
		json.put("password", body.get("password").asText());
		json.put("name", body.get("name").asText());
		ResponseEntity<Object> entity=restTemplate.exchange(
				giturl+"/api/v4/users" ,
				HttpMethod.POST, new HttpEntity<>(json, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "create a user in gitlab");
			json.put("status", "success");
			json.put("message", "successfully user created in gitlab");
		}
		else {
			json.put("context", "create a user in gitlab");
			json.put("status", "failed");
			json.put("message", "unable to create a user");
		}
		return json;
	}	
	public Object deleteUser(JsonNode body, String token) {
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(newtoken);

		ResponseEntity<Object> entity=restTemplate.exchange(
				giturl+"/api/v4/users/" + body.get("id").asText(),
				HttpMethod.DELETE, new HttpEntity<>(header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "delete a user in gitlab");
			json.put("status", "success");
			json.put("message", "user deleted successfully");
		}
		else {
			json.put("context", "delete a user in gitlab");
			json.put("status", "failed");
			json.put("message", "unable to delete a user");
		}
		return json;
	}


	
	public ResponseEntity<List> getUsers(String token) {
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(newtoken);
	
		ResponseEntity<List> entity=restTemplate.exchange(giturl+"/api/v4/users", HttpMethod.GET, new HttpEntity<>(header), List.class);
		
		return entity;
	}
	public Object createGroup(JsonNode body, String token) {
		json.clear();
		HttpHeaders header = new HttpHeaders();
		String newtoken = token.replaceAll("Bearer ", "");
		header.setBearerAuth(newtoken);
		JSONObject json = new JSONObject();
		json.put("name", body.get("name").asText());
		json.put("path", body.get("path").asText());
		json.put("visibility", body.get("visibility").asText());
		
		ResponseEntity<Object> entity=restTemplate.exchange(
				giturl+"/api/v4/groups" ,
				HttpMethod.POST, new HttpEntity<>(json, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "create a group in gitlab ");
			json.put("status", "success");
			json.put("message", "successfully created group in gitlab");
		}
		else {
			json.put("context", "create a group in gitlab");
			json.put("status", "failed");
			json.put("message", "unable to create group");
		}
		return json;
	}
	
	public Object deleteGroup(JsonNode body, String token) {
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(newtoken);

		ResponseEntity<Object> entity=restTemplate.exchange(
				giturl+"/api/v4/groups/" + body.get("id").asText(),
				HttpMethod.DELETE, new HttpEntity<>(header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "delete a group in gitlab");
			json.put("status", "success");
			json.put("message", "group deleted successfully");
		}
		else {
			json.put("context", "delete a group in gitlab");
			json.put("status", "failed");
			json.put("message", "unable to delete group");
		}
		return json;
	}
	public ResponseEntity<List> getGroups(String token) {
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(newtoken);
	
		ResponseEntity<List> entity=restTemplate.exchange(giturl+"/api/v4/groups/", HttpMethod.GET, new HttpEntity<>(header), List.class);
		
		return entity;
	}

	public Object addUserToGroup(JsonNode body, String token) {
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(newtoken);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("user_id", body.get("user_id").asText());
		jsonObj.put("access_level", body.get("access_level").asText());
		ResponseEntity<Object> entity=restTemplate.exchange(
				giturl+"/api/v4/groups/"+body.get("groupid").asText()+"/members",
				HttpMethod.POST, new HttpEntity<>(jsonObj,header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "add a user to a group in gitlab");
			json.put("status", "success");
			json.put("message", "user added to group successfully");
		}
		else {
			json.put("context", "add a user to a group");
			json.put("status", "failed");
			json.put("message", "unable to add user to a group");
		}
		return json;
	}
	public Object removeUserFromGroup(JsonNode body, String token) {
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(newtoken);

		ResponseEntity<Object> entity=restTemplate.exchange(
				giturl+"/api/v4/groups/" + body.get("groupid").asText()+"/members/"+body.get("userid").asText(),
				HttpMethod.DELETE, new HttpEntity<>(header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "remove a user from a group in gitlab");
			json.put("status", "success");
			json.put("message", "user removed from group successfully");
		}
		else {
			json.put("context", "remove a user from a group in gitlab");
			json.put("status", "failed");
			json.put("message", "unable to remove user from group");
		}
		return json;
	}
	public Object approveUser(JsonNode body, String token) {
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(newtoken);
		
		ResponseEntity<Object> entity=restTemplate.exchange(
				giturl+"/api/v4/users/"+body.get("id").asText()+"/approve",
				HttpMethod.POST, new HttpEntity<>(header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "approve a pending user in gitlab");
			json.put("status", "success");
			json.put("message", "user approved successfully");
		}
		else {
			json.put("context", "approve a pending user in gitlab");
			json.put("status", "failed");
			json.put("message", "unable to approve user");
		}
		return json;
	}
	
	public Object addUserToProject(JsonNode body, String token) {
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(newtoken);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("user_id", body.get("user_id").asText());
		jsonObj.put("access_level", body.get("access_level").asText());
		ResponseEntity<Object> entity=restTemplate.exchange(
				giturl+"/api/v4/projects/"+body.get("projectid").asText()+"/members",
				HttpMethod.POST, new HttpEntity<>(jsonObj,header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "add user to a project in gitlab");
			json.put("status", "success");
			json.put("message", "user added to project successfully");
		}
		else {
			json.put("context", "add user to a project in gitlab");
			json.put("status", "failed");
			json.put("message", "unable to add a user to project");
		}
		return json;
	}
	public Object removeUserFromProject(JsonNode body, String token) {
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(newtoken);

		ResponseEntity<Object> entity=restTemplate.exchange(
				giturl+"/api/v4/projects/" + body.get("projectid").asText()+"/members/"+body.get("userid").asText(),
				HttpMethod.DELETE, new HttpEntity<>(header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "remove a user from project in gitlab");
			json.put("status", "success");
			json.put("message", "user removed from project");
		}
		else {
			json.put("context", "remove a user from project in gitlab");
			json.put("status", "failed");
			json.put("message", "unable to remove user from project");
		}
		return json;
	}
	public Object changeAccessOfGroupUser(JsonNode body, String token) {
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(newtoken);
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("access_level", body.get("access_level").asText());
		ResponseEntity<Object> entity=restTemplate.exchange(
				giturl+"/api/v4/groups/"+body.get("groupid").asText()+"/members/"+body.get("user_id").asText(),
				HttpMethod.POST, new HttpEntity<>(jsonObj,header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "change access level of a user in group");
			json.put("status", "success");
			json.put("message", "access level changed successfully");
		}
		else {
			json.put("context", "change access level of a user in group");
			json.put("status", "failed");
			json.put("message", "unable to change access level");
		}
		return json;
	}
	public Object changeAccessOfProjectUser(JsonNode body, String token) {
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(newtoken);
		JSONObject jsonObj = new JSONObject();

		jsonObj.put("access_level", body.get("access_level").asText());
		ResponseEntity<Object> entity=restTemplate.exchange(
				giturl+"/api/v4/projects/"+body.get("projectid").asText()+"/members/"+body.get("user_id").asText(),
				HttpMethod.POST, new HttpEntity<>(jsonObj,header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "change access level of a user in project");
			json.put("status", "success");
			json.put("message", "access level changed successfully");
		}
		else {
			json.put("context", "change access level of a user in group");
			json.put("status", "failed");
			json.put("message", "unable to change access level");
		}
		return json;
	}
	
	public Object mergePullRequest(JsonNode body, String token) {
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(newtoken);
		
		String mergeurl = giturl +"/api/v4/projects/"+ body.get("projectid").asText() + "/merge_requests/" + body.get("id").asText()+"/merge";
		System.out.println(mergeurl);
		ResponseEntity<Object> entity=restTemplate.exchange(mergeurl, HttpMethod.PUT, new HttpEntity<>( header), Object.class);
		
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "merge a pull request in git");
			json.put("status", "success");
			json.put("message", "successfully merged the pull request");
		}
		else {
			json.put("context", "merge a pull request in git");
			json.put("status", "failed");
			json.put("message", "unable to merge the pull request");
		}
		return json;
	}
	public Object assignAssignee(JsonNode body, String token) throws JsonMappingException, JsonProcessingException {
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		 
		header.setBearerAuth(newtoken);
		JSONObject json = new JSONObject();

		  MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
	        formData.add("assignee_ids[]", body.get("assignee_id").asText());
	        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, header);

	        
		System.out.println(json); 
		String assignurl = giturl+"/api/v4/projects/" + body.get("projectid").asText() + "/merge_requests/"+body.get("id").asText();
		System.out.println(assignurl);
		ResponseEntity<String> entity=restTemplate.exchange(assignurl, HttpMethod.PUT, requestEntity, String.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "assign a pull request to a reviewer");
			json.put("status", "success");
			json.put("message", "successfully assigned a reviewer to a pull request");
		}
		else {
			json.put("context", "assign a pull request to a reviewer");
			json.put("status", "failed");
			json.put("message", "unable to assign a reviewer to a pull request");
		}
		return json;
	}
	public Object assignAReviewer(JsonNode body, String token) throws JsonMappingException, JsonProcessingException {
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		 
		header.setBearerAuth(newtoken);
		JSONObject json = new JSONObject();

		  MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
	        formData.add("reviewer_ids[]", body.get("reviewer_id").asText());
	        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, header);

	        
		System.out.println(json); 
		String assignurl = giturl+"/api/v4/projects/" + body.get("projectid").asText() + "/merge_requests/"+body.get("id").asText();
		System.out.println(assignurl);
		ResponseEntity<String> entity=restTemplate.exchange(assignurl, HttpMethod.PUT, requestEntity, String.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "assign a pull request to a reviewer");
			json.put("status", "success");
			json.put("message", "successfully assigned a reviewer to a pull request");
		}
		else {
			json.put("context", "assign a pull request to a reviewer");
			json.put("status", "failed");
			json.put("message", "unable to assign a reviewer to a pull request");
		}
		return json;
	}
	
	/*public Object createTeam(JsonNode data, String token) {
		
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(newtoken);
		JSONObject json = new JSONObject();
		json.put("name", data.get("name").asText());
		json.put("description", data.get("description").asText());
		json.put("permission", data.get("permission").asText());
		json.put("notification_setting", data.get("notification").asText());
		json.put("privacy", data.get("privacy").asText());
		String teamurl="https://api.github.com/orgs/" + data.get("orgname").asText() + "/teams";
		System.out.println("team url is "+teamurl);
		ResponseEntity<Object> entity=restTemplate.exchange(teamurl, HttpMethod.POST,
				new HttpEntity<>(json, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "create a team in git");
			json.put("status", "success");
			json.put("message", "team created successfully");
		}
		else {
			json.put("context", "create a team in git");
			json.put("status", "failed");
			json.put("message", "unable to create team");
		}
		return json;
	}

//adding a user to a teammembership for a user(maintainer,member etc)
//done
	public Object addUserMembership(JsonNode body, String token) {
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(newtoken);
		JSONObject json = new JSONObject();
		json.put("role", body.get("role").asText());
		String memberurl = "https://api.github.com/orgs/" + body.get("orgname").asText() + "/teams/"
				+ body.get("teamname").asText() + "/memberships/" + body.get("username").asText();
		System.out.println(memberurl);
		ResponseEntity<Object> entity=restTemplate.exchange(memberurl, HttpMethod.PUT, new HttpEntity<>(json, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "adding teammembership for a user in git");
			json.put("status", "success");
			json.put("message", "teammembership added successfully");
		}
		else {
			json.put("context", "dding teammembership for a user in git");
			json.put("status", "failed");
			json.put("message", "unable to add teammembership");
		}
		return json;
		
	}
//update team permissions to a repo like read,triage,admin,maintain

	public Object updateTeamPermission(JsonNode body, String token) {
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(newtoken);
		JSONObject json = new JSONObject();
		json.put("permission", body.get("permission").asText());

		String permissionurl = "https://api.github.com/orgs/" + body.get("orgname").asText() + "/teams/"
				+ body.get("teamslug").asText() + "/repos/" + body.get("orgname").asText() + "/"
				+ body.get("repo").asText();
		System.out.println(permissionurl);
		ResponseEntity<Object> entity=restTemplate.exchange(permissionurl, HttpMethod.PUT, new HttpEntity<>(json, header), Object.class);
		
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "update team permissions in git");
			json.put("status", "success");
			json.put("message", "user created successfully");
		}
		else {
			json.put("context", "update team permissions in git");
			json.put("status", "failed");
			json.put("message", "unable to update team permissions");
		}
		return json;
	}

//delete an organization
//done
	public Object deleteOrganization(String orgname, String token) {
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(newtoken);
		ResponseEntity<Object> entity=restTemplate.exchange("https://api.github.com/orgs/" + orgname, HttpMethod.DELETE, new HttpEntity<>(header),
				Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "delete organization in git");
			json.put("status", "success");
			json.put("message", "organization deleted successfully");
		}
		else {
			json.put("context", "delete organization in git");
			json.put("status", "failed");
			json.put("message", "unable to delete organization");
		}
		return json;
	}

//add repository to a team update the team's permission on a repository like pull, triage, push, maintain, admin
	public Object addRepotoTeam(JsonNode body, String token) {
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(newtoken);
		JSONObject json = new JSONObject();
		json.put("permission", body.get("permission").asText());
		// String
		// addurl="https://api.github.com/orgs/"+body.get("orgname").asText()+"/teams/"+body.get("teamslug").asText()+"/repos/"+body.get("owner").asText()+"/"+body.get("repo").asText();
		String addurl = "https://api.github.com/teams/" + body.get("teamid").asText() + "/repos/"
				+ body.get("owner").asText() + "/" + body.get("repo").asText();
		System.out.println(addurl);
		ResponseEntity<Object> entity=restTemplate.exchange(addurl, HttpMethod.PUT, new HttpEntity<>(json, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "add repo to a team in git");
			json.put("status", "success");
			json.put("message", "added repo to team");
		}
		else {
			json.put("context", "add repo to a team in git");
			json.put("status", "failed");
			json.put("message", "unable to add a repo to team");
		}
		return json;
	}

//add or update team membership for a user
	/*
	 * public void addTeamMembership(JsonNode body,String token) { String
	 * newtoken=token.replaceAll("Bearer ", ""); HttpHeaders header=new
	 * HttpHeaders(); header.setBearerAuth(newtoken); JSONObject json=new
	 * JSONObject(); json.put("role", body.get("role").asText()); String
	 * membershipurl="https://api.github.com/orgs/"+body.get("orgname").asText()+
	 * "/teams/"+body.get("teamslug").asText()+"/memberships/"+body.get("username").
	 * asText(); restTemplate.exchange(membershipurl, HttpMethod.PUT,new
	 * HttpEntity<>(json,header),Service.class);
	 * 
	 * }
	 */
//create an organization invitation
//done
	/*public Object createOrganizationInvite(JsonNode body, String token)
			throws JsonMappingException, JsonProcessingException {
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(newtoken);
		JSONObject json = new JSONObject();
		json.put("email", body.get("email").asText());
		json.put("role", body.get("role").asText());
		List<String> list = new ArrayList<String>();

		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(body.get("teamid").asText());

		for (JsonNode j : node) {
			list.add(j.asText());

		}
		ResponseEntity<Object> entity=restTemplate.exchange("https://api.github.com/orgs/" + body.get("orgname").asText() + "/" + "invitations",
				HttpMethod.POST, new HttpEntity<>(json, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "create a organization invite in git");
			json.put("status", "success");
			json.put("message", "successfully created organization invite");
		}
		else {
			json.put("context", "create a organization invite in git");
			json.put("status", "failed");
			json.put("message", "unable to create organization invite");
		}
		return json;
	}

//assign a reviewer to pull request
	
//assign a reviewer to an issue

	public Object assigntoIssue(JsonNode body, String token) throws JsonMappingException, JsonProcessingException {
		json.clear();
		String newtoken = token.replaceAll("Bearer ", "");
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(newtoken);
		JSONObject json = new JSONObject();
		List<String> list = new ArrayList<String>();

		
		
		 
		 
		json.put("assignees",body.get("assignees"));
		//System.out.println(body.get());
		System.out.println(json);
		String issueurl="https://api.github.com/repos/" + body.get("orgname").asText() + "/" + body.get("repo").asText()
				+ "/issues/" + body.get("issuenumber").asText() + "/assignees";
		System.out.println(issueurl);
	ResponseEntity<Object> entity=	restTemplate.exchange(issueurl
				,
				HttpMethod.POST, new HttpEntity<>(json, header), Object.class);
	if(entity.getStatusCode().is2xxSuccessful()) {
		json.put("context", "assign a issue  to a reviewer");
		json.put("status", "success");
		json.put("message", "successfully assigned a reviewer to a issue ");
	}
	else {
		json.put("context", "assign a issue  to a reviewer");
		json.put("status", "failed");
		json.put("message", "successfully assigned a reviewer to a issue ");
	}
	return json;

	}

	// merge a pull request
	// done
	public Object updateVisibilityofRepo(JsonNode body, String token) {
		json.clear();
		HttpHeaders header = new HttpHeaders();
		String newtoken = token.replaceAll("Bearer ", "");
		header.setBearerAuth(newtoken);
		JSONObject json = new JSONObject();
		json.put("description", body.get("description").asText());
		json.put("visibility", body.get("visibility").asText());
		
		json.put("name", body.get("name").asText());
		ResponseEntity<Object> entity=restTemplate.exchange(
				giturl+"/api/v4/groups/"+body.get("id").asText() ,
				HttpMethod.PUT, new HttpEntity<>(json, header), Object.class);
		if(entity.getStatusCode().is2xxSuccessful()) {
			json.put("context", "update visibility of a repo ");
			json.put("status", "success");
			json.put("message", "visibility updated successfully");
		}
		else {
			json.put("context", "update visibility of a repo");
			json.put("status", "failed");
			json.put("message", "unable to update visibility");
		}
		return json;
	}
	*/

}