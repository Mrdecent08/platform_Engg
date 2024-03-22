package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.alertDetails;
import com.example.demo.service.alertService;

@Controller
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
	private List<alertDetails> retrieveAlertByName() {
		return alertSer.getAlertByName();
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
