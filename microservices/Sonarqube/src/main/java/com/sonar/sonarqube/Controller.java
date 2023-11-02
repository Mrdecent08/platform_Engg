package com.sonar.sonarqube;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.sonar.sonarqube.service.Service;


@RestController
@RequestMapping("/sonar")
public class Controller {
	@Autowired
	private Service service;
@PostMapping("/createUser")
public Object createUser(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	
	 return service.createUser(body, token);
}
@PostMapping("/createProject")
public Object createProject(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	 
	return service.createProject(body, token);
}

@PostMapping("/createQualityProfile")
public Object createQualityProfile(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.createQualityProfile(body, token);
}
@PostMapping("/deactivateUser")
public Object deactivateUser(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.deactivateUser(body, token);
}
@PostMapping("/addUser")
public Object addUser(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.addUser(body, token);
}
@DeleteMapping("/deleteGroup")
public Object deleteGroup(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.deleteGroup(body, token);
}
@DeleteMapping("/removeUser")
public Object removeUser(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.removeUser(body, token);
}

@PostMapping("/addProject")
public Object addProject(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.addProject(body, token);
}
@PostMapping("/changeParent")
public Object changeParent(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.changeParent(body, token);
}
@PostMapping("/addGroupPermission")
public Object addGroupPermission(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.addGroupPermission(body, token);
}
@PostMapping("/changeUserPermission")
public Object changeUserPermission(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.changeUserPermission(body, token);
}
@DeleteMapping("/removeGroupPermission")
public Object removeGroupPermission(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.removeGroupPermission(body, token);
}
@DeleteMapping("/removeUserPermission")
public Object removeUserPermission(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.removeUserPermission(body, token);
}
@PostMapping("/updateProjectVisibility")
public Object updateProjectVisibility(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.updateProjectVisibility(body, token);
}
@PostMapping("/createWebhook")
public Object createWebhook(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.createWebhook(body, token);
}
@DeleteMapping("/deleteWebhook")
public Object deleteWebhook(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.deleteWebhook(body, token);
}
@PostMapping("/deleteWebhook")
public Object createToken(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.createToken(body, token);
}

@DeleteMapping("/revokeToken")
public Object revokeToken(@RequestBody JsonNode body,@RequestHeader(name="Authorization") String token) {
	return service.revokeToken(body, token);
}
}
