package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.korReport;
import com.example.demo.service.jenkinsService;
import com.example.demo.service.korService;


@RestController
public class korController {

	private korService korSer;
	
	private jenkinsService jenkinsSer;
	
	public korController(korService korSer,jenkinsService jenkinsSer) {
		super();
		this.korSer = korSer;
		this.jenkinsSer = jenkinsSer;
	}

	@GetMapping("/reports")
	private List<korReport> getAllReports(){
		return korSer.getAllReports();
	}

	@PostMapping("/saveReport")
	private String saveReport(@RequestBody korReport data) {
		return korSer.saveReport(data);
	}
	
	@GetMapping("/getReportById/{id}")
	private korReport getReportByBuildNumber(@PathVariable int id) {
		return korSer.getReportById(id);
	}
	
	@GetMapping("/getReportUrl/{id}")
	public String getReportUrlByBuildNumber(@PathVariable int id) {
		return korSer.getReportUrl(id);
	}
	
	@PostMapping("/generateReport")
	public String generateReport() {
		int buildNumber = Integer.valueOf(jenkinsSer.triggerPipeline());
		
		return "Sample";
	}
}
