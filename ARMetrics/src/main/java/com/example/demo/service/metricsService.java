package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Metrics;


@Service
public interface metricsService {

	List<Metrics> findAllMetrics();

	int findTicketCount();

	int findRemediationCount();

	String updateMetrics(String alertName);

}
