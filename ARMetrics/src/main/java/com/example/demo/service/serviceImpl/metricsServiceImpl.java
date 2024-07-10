package com.example.demo.service.serviceImpl;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Metrics;
import com.example.demo.exception.NoAlertFoundException;
import com.example.demo.repository.metricsRepository;
import com.example.demo.service.metricsService;

@Service
public class metricsServiceImpl implements metricsService{
	
	private metricsRepository metricsRepo;

	List<String> ticket_alerts = Arrays.asList("PVC in pending state","Create Container Config Error","Job Failed","Nodes Are Unavailable","Kubernetes pod with multiple restarts","Endpoint Scraped Multiple Times","Pod count per node high","Kubernetes Node Out Of Storage");
	List<String> remediation_alerts = Arrays.asList("Deployment Replica Mismatch","Hpa running at Max replicas","Kubernetes Pod CrashLooping","Kubernetes Pod Not Healthy","Pod Using Max memory","Pod Using Max CPU","All Replicas On Same Node","Pods stuck at Terminating");
	
	public metricsServiceImpl(metricsRepository metricsRepo) {
		super();
		this.metricsRepo = metricsRepo;
	}

	@Override
	public List<Metrics> findAllMetrics() {
		return metricsRepo.findAll();
	}

	@Override
	public int findTicketCount() {
		return metricsRepo.findByAlertName("ticket").get().getCount();
	}

	@Override
	public int findRemediationCount() {
		return metricsRepo.findByAlertName("remediation").get().getCount();
	}

	@Override
	public String updateMetrics(String alertName) {
		Optional<Metrics> metric = metricsRepo.findByAlertName(alertName);
		if(metric.isEmpty()) {
			Metrics m = new Metrics(alertName,1);
			metricsRepo.save(m);
		}
		else {
			metric.get().setCount(metric.get().getCount()+1);
			metricsRepo.save(metric.get());
		}
		if(ticket_alerts.contains(alertName)) {
			metricsRepo.save(new Metrics(alertName,metricsRepo.findByAlertName("ticket").get().getCount()+1));
		}
		else if(remediation_alerts.contains(alertName)) {
			metricsRepo.save(new Metrics(alertName,metricsRepo.findByAlertName("remediation").get().getCount()+1));
		}
		return "Metrics Updated Successfully";
	}
	
	
	
}
