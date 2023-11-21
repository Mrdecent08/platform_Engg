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
//need to check
@PatchMapping("/updateVisibility")
public Object updateVisibility(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.updateVisibility(body, token);
}
@PostMapping("/createUser")
public Object createUser(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.createUser(body, token);
}
@DeleteMapping("/deleteUser")
public Object deleteUser(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.deleteUser(body, token);
}
@GetMapping("/getUser")
public ResponseEntity<List> getUser(@RequestHeader(name="Authorization") String token) {
	return service.getUsers(token);
}
@PostMapping("/createGroup")
public Object createGroup(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.createGroup(body, token);
}
@DeleteMapping("/deleteGroup")
public Object deleteGroup(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.deleteGroup(body, token);
}
@GetMapping("/getGroups")
public ResponseEntity<List> getGroups(@RequestHeader(name="Authorization") String token) {
	return service.getGroups(token);
}
@PostMapping("/approveUser")
public Object approveUser(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.approveUser(body, token);
}
@PostMapping("/addUserToGroup")
public Object addUserToGroup(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.addUserToGroup(body, token);
}
@DeleteMapping("/removeUserFromGroup")
public Object removeUserFromGroup(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.removeUserFromGroup(body, token);
}

@PostMapping("/addUserToProject")
public Object addUserToProject(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.addUserToProject(body, token);
}
@DeleteMapping("/removeUserFromProject")
public Object removeUserFromProject(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.removeUserFromProject(body, token);
}
@PutMapping("/changeAccessOfGroupUser")
public Object changeAccessOfGroupUser(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.changeAccessOfGroupUser(body, token);
}
@PutMapping("/changeAccessOfProjectUser")
public Object changeAccessOfProjectUser(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.changeAccessOfProjectUser(body, token);
}
@PostMapping("/mergePullRequest")
public Object mergePullRequest(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.mergePullRequest(body, token);
}
@PostMapping("/assignAssignee")
public Object assignAssignee(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) throws JsonMappingException, JsonProcessingException {
	return service.assignAssignee(body, token);
}
@PostMapping("/assignReviewer")
public Object assignReviewer(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) throws JsonMappingException, JsonProcessingException {
	return service.assignAReviewer(body, token);
}
/*
@PostMapping("/createTeam")
public Object createTeam(@RequestHeader(name="Authorization") String token,@RequestBody JsonNode data) {
	System.out.println("jjfjee");
	return service.createTeam(data, token);
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
/*
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
}*/
}
