package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import io.micrometer.core.instrument.MeterRegistry;

@SpringBootApplication
public class PrometheusAlertsApplication {

	private RestTemplate restTemplate = new RestTemplate();
	private String alertmanager_host = "alertmanager.grafana.svc.cluster.local";
	private String alertmanager_port = "9093";

	@PostMapping("/alert-hook")
	public void receiveAlertHook(@RequestBody Map<String,Object> request) throws Exception {
		System.out.println(request);
	}

	@GetMapping("/get-alerts")
	public String receiveAllAlerts() {
		String alertManagerUrl = "http://"+alertmanager_host+":"+alertmanager_port+"/api/v2/alerts/groups";
		ResponseEntity<String> response = restTemplate.getForEntity(alertManagerUrl, String.class);
		return response.getBody();
	}
	
	 @GetMapping("/alertsSummary")
     public List<String> SummaryOfAlerts() {
//           String alertManagerUrl = "http://"+alertmanager_host+":"+alertmanager_port+"/api/v2/alerts/groups";
             String alertManagerUrl = "http://10.63.33.181:30279/api/v2/alerts/groups";
             ResponseEntity<String> response = restTemplate.getForEntity(alertManagerUrl, String.class);
             AlertService alertService = new AlertService();
             return alertService.extractAlertNames(response.getBody());
     }

	public static void main(String[] args) {
		SpringApplication.run(PrometheusAlertsApplication.class, args);
	}

}
