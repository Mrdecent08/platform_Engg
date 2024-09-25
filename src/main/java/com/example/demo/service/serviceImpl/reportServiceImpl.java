package com.example.demo.service.serviceImpl;

import java.util.List;

import com.example.demo.entity.reportEntity;
import com.example.demo.repository.reportRepository;
import com.example.demo.service.reportService;

public class reportServiceImpl implements reportService{

	private reportRepository reportRepo;
	
	public reportServiceImpl(reportRepository reportRepo) {
		super();
		this.reportRepo = reportRepo;
	}

	@Override
	public void saveData(reportEntity data) {
		reportRepo.save(data);
	}

	@Override
	public List<reportEntity> retrieveData() {
		return reportRepo.findAll();
		
	}

}
