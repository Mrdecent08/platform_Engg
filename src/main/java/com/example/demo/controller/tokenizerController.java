package com.example.demo.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Budgets;
import com.example.demo.service.tokenizerService;


@RestController
public class tokenizerController {

        private tokenizerService tokenizerService;

        public tokenizerController(com.example.demo.service.tokenizerService tokenizerService) {
                super();
                this.tokenizerService = tokenizerService;
        }

        @GetMapping("/calculateTokens")
        private double calculateTokens(String query) {
                return tokenizerService.calculateTokens(query);
        }

        @GetMapping("/tokens")
        private void updateTokens(String requestBody) {
        	JSONObject jsonObject = new JSONObject(requestBody);
            tokenizerService.updateTokens(jsonObject.get("projectName").toString(),jsonObject.get("prompt").toString(),jsonObject.get("username").toString());
        }
        
        @PostMapping("/model")
        private String queryModel(@RequestBody String requestBody) {
                JSONObject jsonObject = new JSONObject(requestBody);
                return tokenizerService.queryModel(jsonObject.get("projectName").toString(),jsonObject.get("model").toString(),jsonObject.get("prompt").toString(),jsonObject.get("username").toString());
        }
        
        @GetMapping("/getProjects")
        private List<Budgets> getAllProjects(){
        	return tokenizerService.getAllProjects();
        }
        
        @PostMapping("/addProject")
        private Budgets saveProject(@RequestBody Budgets project) {
        	return tokenizerService.saveProject(project);
        }
        
        @PutMapping("/updateProject")
        private Budgets updateProject(@RequestBody Budgets project) {
        	return tokenizerService.updateProject(project);
        }
        
        @GetMapping("/consumeService")
        private void consumedService(String requestBody) {
        	JSONObject jsonObject = new JSONObject(requestBody);
            tokenizerService.updateConsumption(jsonObject.get("projectName").toString(),jsonObject.get("service").toString());
        }

}