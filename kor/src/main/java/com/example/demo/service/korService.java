package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.korReport;

@Service
public interface korService {

	List<korReport> getAllReports();

	String saveReport(korReport data);

	korReport getReportById(int id);

}
