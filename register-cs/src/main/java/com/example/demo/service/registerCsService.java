package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.registerDetails;

public interface registerCsService {

	String createApplication(registerDetails details);

	List<registerDetails> getAllApplicationsDetails();

}
