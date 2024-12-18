package com.example.demo.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;



@Service
public interface tokenizerService {

	double calculateTokens(String query);

	String queryModel(String projectName,String model,String prompt);

	

}
