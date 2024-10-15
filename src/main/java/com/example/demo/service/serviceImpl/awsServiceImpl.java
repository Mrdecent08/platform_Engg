package com.example.demo.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.awsEntity;
import com.example.demo.exception.NoReportFoundException;
import com.example.demo.repository.awsRepository;
import com.example.demo.service.awsService;

@Service
public class awsServiceImpl implements awsService {

	private awsRepository awsRepo;

	
	public awsServiceImpl(awsRepository awsRepo) {
		super();
		this.awsRepo = awsRepo;
	}


	@Override
	public List<awsEntity> getAllReports() {
		List<awsEntity> result = awsRepo.findAll();
		if(result.isEmpty()) {
			throw new NoReportFoundException("No data exists");
		}
		return result;
	}


	@Override
	public String saveReport(awsEntity data) {
		Optional<awsEntity> report = awsRepo.findByVolumeId(data.getVolumeId());
		if(report.isEmpty()) {
			awsRepo.save(data);
			
		}
		else {
			awsEntity curr_report = report.get();
			curr_report.setMachineId(data.getMachineId());
			curr_report.setMachineIp(data.getMachineIp());
			curr_report.setRecommendation(data.getRecommendation());
			curr_report.setStorageAllocated(data.getStorageAllocated());
			curr_report.setStorageUtilized(data.getStorageUtilized());
			curr_report.setUtilizationPercentage(data.getUtilizationPercentage());
			awsRepo.save(curr_report);
		}
		return "Data Added Successfully";
	}


	@Override
	public String deleteAll() {
		awsRepo.deleteAll();
		return "Successfully Deleted All the Data in the table";
	}

	
	
}
