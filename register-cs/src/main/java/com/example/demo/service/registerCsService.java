package com.example.demo.service;

import com.example.demo.entity.registerDetails;

public interface registerCsService {

	String createApplication(registerDetails details);

	Object getAllApplicationsDetails();

}
