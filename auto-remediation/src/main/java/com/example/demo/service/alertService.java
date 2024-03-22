package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.alertDetails;

public interface alertService {

	String addAlert(alertDetails alertData);

	List<alertDetails> getAllAlerts();

	List<alertDetails> getAlertByName();

	alertDetails getAlertById(int id);

	String updateAlertById(alertDetails alertData);

	String deleteAlertById(int id);

}
