package com.example.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.registerDetails;
import com.example.demo.service.registerCsService;
import com.example.demo.service.userService;

@RestController
public class resgisterCsController {

	private registerCsService registerService;
	
	public resgisterCsController(registerCsService registerService,userService userService) {
		super();
		this.registerService = registerService;
	}
	
	@PostMapping("/addApplication")
	public String createApplication(@RequestBody registerDetails details) throws IOException {
		return registerService.createApplication(details);
	}
	
	@PutMapping("/updateApplication")
	public String updateApplication(@RequestBody registerDetails details) {
		details.setToken("token");
		return registerService.updateApplication(details);
	}
	
	@DeleteMapping("/deleteApplication/{id}")
	public String deleteApplication(@PathVariable int id) {
		return registerService.deleteApplication(id);
	}
	
	@GetMapping("/getApplicationById/{id}")
	public Optional<registerDetails> getApplicationById(@PathVariable int id) {
		return registerService.getAllApplicationsDetailsById(id);
	}
	@GetMapping("/getAllApplications")
	public List<registerDetails> getAllApplications() {
		return registerService.getAllApplicationsDetails();
	}
}
