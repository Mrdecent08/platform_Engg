package com.example.demo.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.korReport;
import com.example.demo.exception.NoReportFoundException;
import com.example.demo.repository.korRepository;
import com.example.demo.service.korService;

@Service
public class korServiceImpl implements korService{
	
	private korRepository korRepo;
	
	

	public korServiceImpl(korRepository korRepo) {
		super();
		this.korRepo = korRepo;
	}



	@Override
	public List<korReport> getAllReports() {
		return korRepo.findAll();
	}



	@Override
	public String saveReport(korReport data) {
		korRepo.save(data);
		return "Report Stored Successfully";
	}



	@Override
	public korReport getReportById(int id) {
		Optional<korReport> report = korRepo.findById(id);
		if(report.isEmpty()) {
			throw new NoReportFoundException("Report does not exist !!! ");
		}
		return report.get();
	}

}
