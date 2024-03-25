package com.example.demo;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class alertService {

	@PostMapping("/alertHook")
	public void receiveAlertHook(@RequestBody Map<String, Object> request) throws Exception {
		System.out.println(request.get("alerts"));
	}
}
