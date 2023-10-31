package com.platform.git.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.platform.git.data.GitData;
import com.platform.git.service.Service;


@RestController
@RequestMapping("/git")
public class Controller {
	@Autowired
	private Service service;
@PostMapping("/createrepo")
public Object createRepo(@RequestHeader(name="Authorization") String token,@RequestBody JsonNode data) {
	return  service.createRepo(data,token);
}
//display list of users
@GetMapping("/get")
public ResponseEntity<List> get(@RequestHeader(name="Authorization") String token) {
	return  service.get(token);
}
@DeleteMapping("/deleteRepo")
public Object deleteRepo(@RequestBody JsonNode data,@RequestHeader(name="Authorization") String token) {
	return service.deleteRepo(data,token);
}
@PostMapping("/createTeam")
public Object createTeam(@RequestHeader(name="Authorization") String token,@RequestBody JsonNode data) {
	System.out.println("jjfjee");
	return service.createTeam(data, token);
}
@PatchMapping("/updateVisibility")
public Object updateVisibility(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.updateVisibility(body, token);
}
@PutMapping("/addUserMembership")
public Object addUserMembership(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token ) {
	return service.addUserMembership(body, token);
}
//not needed
@DeleteMapping("/deleteOrganization/{orgname}")
public Object deleteOrganization(@PathVariable(name="orgname") String name,@RequestHeader(name="Authorization") String token) {
	return service.deleteOrganization(name,token);
}
@PutMapping("/updateTeamPermission")
public Object updateTeamPermission(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token ) {
	return service.updateTeamPermission(body, token);
}
@PutMapping("/addRepotoTeam")
public Object addRepotoTeam(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token ) {
	return service.addRepotoTeam(body, token);
}

	/*
	 * @PutMapping("/addTeamMembership") public void addTeamMembership(@RequestBody
	 * JsonNode body,@RequestHeader(name="Authorization") String token ) {
	 * return service.addTeamMembership(body, token); }
	 */
@PostMapping("/createOrganizationInvite")
public Object createOrganizationInvite(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token ) throws JsonMappingException, JsonProcessingException {
	return service.createOrganizationInvite(body, token);
}
@PostMapping("/assignReviewer")
public Object assignReviewer(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token ) throws JsonMappingException, JsonProcessingException {
	return service.assignReviewer(body, token);
}
@PostMapping("/assigntoIssue")
public Object assigntoIssue(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token ) throws JsonMappingException, JsonProcessingException {
	return service.assigntoIssue(body, token);
}
@PutMapping("/mergePullRequest")
public Object mergePullRequest(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token ) {
	return service.mergePullRequest(body, token);
}
}
