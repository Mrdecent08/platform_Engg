package com.example.demo.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

        @PostMapping("/model")
        private String queryModel(@RequestBody String requestBody) {
                JSONObject jsonObject = new JSONObject(requestBody);
                return tokenizerService.queryModel(jsonObject.get("projectName").toString(),jsonObject.get("model").toString(),jsonObject.get("prompt").toString());
        }

}