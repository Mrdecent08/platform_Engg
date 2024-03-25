package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.alertDetails;

@Service
public interface alertService {

	String addAlert(alertDetails alertData);

	List<alertDetails> getAllAlerts();

	List<alertDetails> getAlertByName(String alertName);

	alertDetails getAlertById(int id);

	String updateAlertById(alertDetails alertData);

	String deleteAlertById(int id);

	alertDetails getAlertByNameAndCategory(String alertName, String category);

}
