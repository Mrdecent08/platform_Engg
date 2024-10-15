package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.awsEntity;
import com.example.demo.service.awsService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
public class awsController {

	private awsService awsSer;

	RestTemplate restTemplate = new RestTemplate();

	public awsController(awsService awsSer) {
		super();
		this.awsSer = awsSer;
	}

	@GetMapping("/")
	private List<awsEntity> getAllReports() {
		return awsSer.getAllReports();
	}

	@PostMapping("/save")
	private String save(@RequestBody awsEntity data) {
		System.out.println(data);
		return awsSer.saveReport(data);
	}

	@PostMapping("/createEnv")
	private String saveReport(@RequestBody awsEntity data) {
		System.out.println(data);
		return awsSer.saveReport(data);
	}
	
	@Hidden
	@DeleteMapping("/deleteAll")
	private String deleteAll() {
		return awsSer.deleteAll();
	}

}
