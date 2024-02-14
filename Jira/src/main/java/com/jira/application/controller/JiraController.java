package com.jira.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import com.jira.application.service.JiraService;

@RestController
public class JiraController {
	@Autowired
	private JiraService jiraService;
	 @PostMapping("/projects")
	    public void createProject(@RequestBody JsonNode body) {
	      // Create a new project in Jira
	     
		jiraService.createProject(body);
	    }
	 @PostMapping("/issues")
	 public void createIssue(@RequestBody JsonNode body) {
		 jiraService.createIssue(body);
	 }
}
