package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.userDetails;
import com.example.demo.service.userService;

@RestController
public class userController {

	private userService userService;

	
	public userController(userService userService) {
		super();
		this.userService = userService;
	}

	
	@GetMapping("/userDetails")
	public List<userDetails> getAlluserDetailss() {
		return userService.getAlluserDetails();
	}
	
	
	@PostMapping("/saveUser")
	public userDetails saveuserDetails(@RequestBody userDetails userDetails) {
			return userService.saveuserDetails(userDetails);
	}
	
	
}
