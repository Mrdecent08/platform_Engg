package com.platformengneering.nexus.controller;

import java.util.Base64;

import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.platformengneering.nexus.service.Service;

@RestController
@RequestMapping("/nexus")
public class Controller {
	@Autowired
	private Service service;
	//done
@PostMapping("/createUser")
public Object createUser(@RequestHeader("Authorization") String token,@RequestBody JsonNode body) throws JsonMappingException, JsonProcessingException {
	System.out.println("roles"+	body.get("roles").asText());
	return service.createUser(token, body);
	//return "djhd";

}
//done
@DeleteMapping("/deleteUser/{userId}")
public Object deleteUser(@RequestHeader("Authorization") String token,@PathVariable("userId")String userId) {
	return service.deleteUser(token,userId
			);
}
//done
@PutMapping("/changePassword/{userId}")
public Object changePassword(@RequestHeader("Authorization") String token,@PathVariable("userId")String userId,@RequestBody String password) {
	return service.changePassword(token,userId,password);
}
//done
@PutMapping("/updateUser/{userId}")
public Object updateUser(@RequestHeader("Authorization") String token,@PathVariable("userId") String userId,@RequestBody JsonNode body) {
	return service.updateUser(token,userId, body);
}
//done
@PostMapping("/createRole")
public Object createRole(@RequestHeader("Authorization") String token,@RequestBody JsonNode body) {
	return service.createRole(token, body);
}
//done
@DeleteMapping("/deleteRole/{Id}")
public Object deleteRole(@RequestHeader("Authorization") String token,@PathVariable("Id") String Id) {
	return service.deleteRole(token,Id);
}
//done
@PostMapping("/updateRole")
public Object updateRole(@RequestHeader("Authorization") String token,@RequestBody JsonNode body) {
	return service.updateRole(token, body);
}
//done
@PostMapping("/createPrivilaege")
public Object createPrivilaege(@RequestHeader("Authorization") String token,@RequestBody JsonNode body) {
	return service.createPrivilaege(token, body);
}
@DeleteMapping("/deleteprivilege/{privilegename}")
public Object deleteprivilege(@RequestHeader("Authorization") String token,@PathVariable("privilegename") String privilegename) {
	return service.deleteprivilege(token, privilegename);
}
@PostMapping("/createazureblobStorage")
public Object createazureblobStorage(@RequestHeader("Authorization") String token,@RequestBody JsonNode body) {
	return service.createazureblobStorage(token, body);
}

//done
@DeleteMapping("/deleteblobstores/{name}")
public Object deleteblobstores(@RequestHeader("Authorization") String token,@PathVariable("name") String name) {
	return service.deleteblobstores(token,name);
}
//done

//done
@PostMapping("/createContentSelector")
public Object createContentSelector(@RequestHeader("Authorization") String token,@RequestBody JsonNode body) {
	return service.createContentSelector(token, body);
}
//done
@DeleteMapping("/deleteContentSelector/{name}")
public Object deleteContentSelector(@RequestHeader("Authorization") String token,@PathVariable("name") String name) {
	return service.deleteContentSelector(token, name);
}
//done
@PostMapping("/createRoutingRules")
public Object createRoutingRules(@RequestHeader("Authorization") String token,@RequestBody JsonNode body) {
	return service.createRoutingRules(token, body);
}
//done
@DeleteMapping("/deleteRoutingRules/{name}")
public Object deleteRoutingRules(@RequestHeader("Authorization") String token,@PathVariable("name") String  name) {
	return service.deleteRoutingRules(token, name);
}
//done
@PutMapping("/updateAnonymousAccess")
public Object updateAnonymousAccess(@RequestHeader("Authorization") String token,@RequestBody JsonNode body) {
	return service.updateAnonymousAccess(token, body);
}
@PostMapping("/test")
public void test(@RequestHeader("Authorization") String token) {
	
}
@GetMapping("/print-credentials")
public String printCredentials(@RequestHeader("Authorization") String authorizationHeader) {
    if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
        // Extract the Base64-encoded credentials part (after "Basic ")
        String base64Credentials = authorizationHeader.substring("Basic ".length()).trim();
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(decodedBytes);
        String[] credentialsArray = credentials.split(":", 2); // Split username and password

        if (credentialsArray.length == 2) {
            String username = credentialsArray[0];
            String password = credentialsArray[1];

            // Print the username and password as headers
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);

            return "Credentials printed in the console.";
        }
    }

    // Handle invalid or missing credentials here
    return "Invalid or missing credentials.";
}
}
