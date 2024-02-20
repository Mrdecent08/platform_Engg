package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.registerDetails;
import com.example.demo.service.registerCsService;

@RestController
public class resgisterCsController {

	private registerCsService registerService;

	public resgisterCsController(registerCsService registerService) {
		super();
		this.registerService = registerService;
	}
	
	@PostMapping("/addApplication")
	public String createApplication(@RequestBody registerDetails details) {
		details.setToken("token");
		return registerService.createApplication(details);
	}
	
	@GetMapping("getAllApplications")
	public Object getAllApplications() {
		return registerService.getAllApplicationsDetails();
	}
}
