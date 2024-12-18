package com.example.demo.service.serviceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.Budgets;
import com.example.demo.exception.FailedToExecuteTokens;
import com.example.demo.exception.NoProjectFoundException;
import com.example.demo.exception.TokenLimitExceeded;
import com.example.demo.repository.tokenizerRepository;
import com.example.demo.service.tokenizerService;

@Service
public class tokenizerServiceImpl implements tokenizerService{

	private tokenizerRepository tokenizerRepository;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public tokenizerServiceImpl(com.example.demo.repository.tokenizerRepository tokenizerRepository) {
		super();
		this.tokenizerRepository = tokenizerRepository;
	}

	 public String generateResponse(String model, String prompt) {
        String url = "http://10.63.20.98:32441/api/generate";

        String payload = String.format("{\"model\":\"%s\",\"prompt\":\"%s\",\"stream\":false}", model, prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );

        return response.getBody();
	 }

	@Override
	public double calculateTokens(String query) {
		

		StringBuilder output = new StringBuilder();
        try {
            // Path to the Python script and interpreter
            String pythonScriptPath = "./token_counter.py"; // Update with actual script path
            String pythonInterpreter = "python3"; // Ensure python3 is installed and accessible

            // Build the process to execute the Python script
            ProcessBuilder processBuilder = new ProcessBuilder(pythonInterpreter, pythonScriptPath, query);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            // Read the output of the Python script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new FailedToExecuteTokens("Failed to Execute Python File");
            }
        } catch (Exception ex) {
        	throw new FailedToExecuteTokens("Failed to Execute Python File \n" + ex.getMessage());
        }
        System.out.println(output.toString());
        return Double.valueOf(output.toString());
		
	}


	@Override
	public String queryModel(String projectName,String model, String prompt) {
		double tokens = calculateTokens(prompt);
		Optional<Budgets> budgets = tokenizerRepository.findByProjectName(projectName);
		if(budgets.isEmpty()) {
			throw new NoProjectFoundException("No Project With Name : "+ projectName);
		}		
		double remainingTokens =  budgets.get().getRemainingTokens();
		if(remainingTokens < tokens) {
			throw new TokenLimitExceeded("Token Limit Exceeded !! ");
		}
		budgets.get().setRemainingTokens(remainingTokens-tokens);
		tokenizerRepository.save(budgets.get());
		JSONObject jsonObject = new JSONObject(generateResponse(model, prompt));
		return jsonObject.getString("response").toString();
	}
	
	@Override
	public void updateTokens(String projectName, String prompt) {
		double tokens = calculateTokens(prompt);
		Optional<Budgets> budgets = tokenizerRepository.findByProjectName(projectName);
		if(budgets.isEmpty()) {
			throw new NoProjectFoundException("No Project With Name : "+ projectName);
		}		
		double remainingTokens =  budgets.get().getRemainingTokens();
		if(remainingTokens < tokens) {
			throw new TokenLimitExceeded("Token Limit Exceeded !! ");
		}
		budgets.get().setRemainingTokens(remainingTokens-tokens);
		tokenizerRepository.save(budgets.get());
	}
	
	@Override
	public List<Budgets> getAllProjects() {
		return tokenizerRepository.findAll();
	}

	@Override
	public Budgets saveProject(Budgets project) {
		
		Optional<Budgets> currProject = tokenizerRepository.findByProjectName(project.getProjectName());
		if(currProject.isEmpty()) {
			project.setRemainingTokens(project.getTokenLimit());
			return tokenizerRepository.save(project);
		}
		Budgets newProject = currProject.get();
		newProject.setbudget(project.getbudget());
		newProject.setTokenLimit(project.getTokenLimit());
		newProject.setRemainingTokens(currProject.get().getRemainingTokens()+project.getTokenLimit()-currProject.get().getTokenLimit());
		return tokenizerRepository.save(newProject);
	}

	@Override
	public Budgets updateProject(Budgets project) {
		Optional<Budgets> currProject = tokenizerRepository.findByProjectName(project.getProjectName());
		if(currProject.isEmpty()) {
			throw new NoProjectFoundException("No Project Found !!!");
		}
		Budgets newProject = currProject.get();
		newProject.setbudget(project.getbudget());
		newProject.setTokenLimit(project.getTokenLimit());
		newProject.setRemainingTokens(currProject.get().getRemainingTokens()+project.getTokenLimit()-currProject.get().getTokenLimit());
		return tokenizerRepository.save(newProject);
	}

	

}
	
	
