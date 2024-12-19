package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Users;
import com.example.demo.service.userService;

@RestController
public class userController {

	private userService userService;

	public userController(com.example.demo.service.userService userService) {
		super();
		this.userService = userService;
	}
	
	@GetMapping("/getUsers")
	private List<Users> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@PostMapping("/addUser")
	private Users saveUser(@RequestBody Users user) {
		return userService.save(user);
		
	}
	
	@PutMapping("/updateUser")
	private Users updateUser(@RequestBody Users user) {
		return userService.updateUser(user);
		
	}
}
