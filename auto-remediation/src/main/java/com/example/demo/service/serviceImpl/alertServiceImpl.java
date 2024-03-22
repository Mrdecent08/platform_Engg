package com.example.demo.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.alertDetails;
import com.example.demo.exception.NoAlertFoundException;
import com.example.demo.repository.alertRepository;
import com.example.demo.service.alertService;

@Service
public class alertServiceImpl implements alertService{
	
	private alertRepository alertRepo;
	

	@Override
	public String addAlert(alertDetails alertData) {
		alertRepo.save(alertData);
		return "Alert Data Added Successfully !!";
	}

	@Override
	public List<alertDetails> getAllAlerts() {
		return alertRepo.findAll();
	}

	@Override
	public List<alertDetails> getAlertByName(String alertName) {
		return alertRepo.findAlertByAlertname(alertName);
	}

	@Override
	public alertDetails getAlertById(int id) {
		Optional<alertDetails> data = alertRepo.findById(id);
		if(data.isEmpty()) {
			throw new NoAlertFoundException("No Alert Found with ID: "+ id);
		}
		return data.get();
	}

	@Override
	public String updateAlertById(alertDetails alertData) {
		Optional<alertDetails> data = alertRepo.findById(alertData.getId());
		if(data.isEmpty()) {
			throw new NoAlertFoundException("No Alert Found with ID: "+ alertData.getId());
		}
		alertRepo.save(alertData);
		return "Alert Updated Successfully !!";
	}

	@Override
	public String deleteAlertById(int id) {
		Optional<alertDetails> data = alertRepo.findById(id);
		if(data.isEmpty()) {
			throw new NoAlertFoundException("No Alert Found with ID: "+ id);
		}
		alertRepo.deleteById(id);
		return "Alert Deleted Successfully!!";
	}

}
