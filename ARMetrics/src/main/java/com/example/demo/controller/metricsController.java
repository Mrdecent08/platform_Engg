package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Metrics;
import com.example.demo.service.metricsService;

@RestController
public class metricsController {

	private metricsService metricsSer;

	public metricsController(metricsService metricsSer) {
		super();
		this.metricsSer = metricsSer;
	}

	@GetMapping("/metrics")
	public List<Metrics> getAllMetrics(){
		return metricsSer.findAllMetrics();
	}
	
	@GetMapping("/resolveTicket")
	public int getTicketCount() {
		return metricsSer.findTicketCount();
	}
	
	@GetMapping("/resolveRemediation")
	public int getRemediationCount() {
		return metricsSer.findRemediationCount();
	}
	
	@PostMapping("/updateMetrics")
	public String updateMetrics(@RequestParam String alertName) {
		return metricsSer.updateMetrics(alertName);
	}
}
