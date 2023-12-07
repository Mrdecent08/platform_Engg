package com.example.demo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("enc-dec")
public class testingCryptography {

	RestTemplate restTemplate = new RestTemplate();
	String apiGatewayUrl = "aa7eabfa05ed24fc3ad6d2c4007e805c-1204085443.us-east-1.elb.amazonaws.com";
	HttpHeaders headers = new HttpHeaders();
	
	@GetMapping("/ecryption")
	public String encryptData(@RequestBody String data){
		headers.set("host","cryptography.example.com");
		ResponseEntity<String> encryptedData = restTemplate.exchange(apiGatewayUrl+"/data-encrypt/encrypt", HttpMethod.POST, new HttpEntity<>(data,headers),
				String.class);
		return encryptedData.getBody();
		
	}
}
