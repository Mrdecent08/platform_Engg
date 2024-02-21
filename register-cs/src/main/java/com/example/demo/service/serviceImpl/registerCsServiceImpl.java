package com.example.demo.service.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.registerDetails;
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
		// TODO Auto-generated method stub
		System.out.print(registerRepository.findById(1));
		return registerRepository.findAll();
	}
	
	

}
