package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.JiraTicket;
import com.example.demo.service.JiraService;

@RestController
@RequestMapping("/api/jira")
public class JiraController {

    @Autowired
    private JiraService jiraService;

    @PostMapping("/createTicket")
    public ResponseEntity<String> createTicket(@RequestBody JiraTicket ticket) {
        try {
            String result = jiraService.createTicket(ticket);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
