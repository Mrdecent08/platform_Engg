package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.awsEntity;


@Service
public interface awsService {

	List<awsEntity> getAllReports();

	String saveReport(awsEntity data);

	String deleteAll();

	

}
