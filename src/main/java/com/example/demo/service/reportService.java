package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.reportEntity;

@Service
public interface reportService {

	void saveData(reportEntity data);

	List<reportEntity> retrieveData();

}
