package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.registerDetails;

public interface registerCsService {

	String createApplication(registerDetails details);

	List<registerDetails> getAllApplicationsDetails();

	String updateApplication(registerDetails details);

	String deleteApplication(int id);

	Optional<registerDetails> getAllApplicationsDetailsById(int id);

}
