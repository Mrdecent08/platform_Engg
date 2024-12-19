package com.example.demo.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Budgets;



@Service
public interface tokenizerService {

	double calculateTokens(String query);

	String queryModel(String projectName,String model,String prompt, String username);

	List<Budgets> getAllProjects();

	Budgets saveProject(Budgets project);

	Budgets updateProject(Budgets project);

	void updateTokens(String projectName, String prompt, String username);

	void updateConsumption(String projectName, String service);

	

}
