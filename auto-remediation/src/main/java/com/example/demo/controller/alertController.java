package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.alertDetails;
import com.example.demo.service.alertService;

@RestController
public class alertController {

	private alertService alertSer;
	
	public alertController(alertService alertSer) {
		super();
		this.alertSer = alertSer;
	}

	@GetMapping("/retrieveAlerts")
	private List<alertDetails> retrieveAlerts(){
		return alertSer.getAllAlerts();
	}

	@GetMapping("/retrieveAlertByName")
	private List<alertDetails> retrieveAlertByName(@RequestParam String alertName) {
		return alertSer.getAlertByName(alertName);
	}
	
	@GetMapping("/retrieveAlertByNameAndCategory")
	private alertDetails retrieveAlertByNameAndCategory(@RequestParam String alertName,@RequestParam String category) {
		return alertSer.getAlertByNameAndCategory(alertName,category);
	}
	
	@GetMapping("/retrieveAlertById")
	private alertDetails retrieveAlertById(@RequestParam int id) {
		return alertSer.getAlertById(id);
	}
	
	@PostMapping("/postAlert")
	public String addAlert(@RequestBody alertDetails alertData) {
		return alertSer.addAlert(alertData);
	}
	
	@PutMapping("/updateAlertById")
	private String updateAlertById(@RequestBody alertDetails alertData) {
		return alertSer.updateAlertById(alertData);
	}
	
	@DeleteMapping("/deleteAlertById")
	private String deleteAlertById(@RequestParam int id) {
		return alertSer.deleteAlertById(id);
	}
	
	
}
