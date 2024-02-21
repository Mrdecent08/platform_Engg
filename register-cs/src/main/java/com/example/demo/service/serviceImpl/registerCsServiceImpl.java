package com.example.demo.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.registerDetails;
import com.example.demo.exception.NoApplicationFoundException;
import com.example.demo.repository.registerDetailsRepository;
import com.example.demo.service.registerCsService;

@Service
public class registerCsServiceImpl implements registerCsService {

	private registerDetailsRepository registerRepository;

	public registerCsServiceImpl(registerDetailsRepository registerRepository) {
		super();
		this.registerRepository = registerRepository;
	}

	@Override
	public String createApplication(registerDetails details) {
		registerRepository.save(details);
		return "Application Added Successfully";
	}

	@Override
	public List<registerDetails> getAllApplicationsDetails() {
		return registerRepository.findAll();
	}

	@Override
	public String updateApplication(registerDetails details) {
		int id = details.getId();
		Optional<registerDetails> detail = registerRepository.findById(id);
		if(detail.isEmpty()) {
			throw new NoApplicationFoundException("No Application with ID: "+id);
		}
		else {
			registerRepository.save(details);
			return "Details Are Updated Successfully";
		}
		
	}

	@Override
	public String deleteApplication(int id) {
		registerRepository.deleteById(id);
		return "Application Deleted Successfully";
	}

	@Override
	public Optional<registerDetails> getAllApplicationsDetailsById(int id) {
		Optional<registerDetails> details = registerRepository.findById(id);
		if(details.isEmpty()) {
			throw new NoApplicationFoundException("No Application with ID: "+id);
		}
		else {
			return details;
		}
	}
	
	

}
