package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/jenkins")
public class JenkinsController {
	
    private final String jenkinsUrl = "http://10.63.20.122:8080/job/";

    @PostMapping("/trigger")
    public ResponseEntity<String> triggerJenkinsJob(@RequestParam String applicationName,@RequestParam String cloud) {
    	
    	String jenkinsJobUrl = jenkinsUrl + applicationName + "/build";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(jenkinsJobUrl, null, String.class);

        return ResponseEntity.ok("Jenkins job triggered. Response: " + response.getBody());
    }
}
