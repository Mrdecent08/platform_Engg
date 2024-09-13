package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Utils.JenkinsOps;
import com.example.demo.entity.korReport;

@RestController
@RequestMapping("/jenkins")
public class JenkinsController {


    @Value("${platform.pipeline.createReport}")
    private String createReportPipeline;

    @Autowired
    private JenkinsOps jenkinsOps;

    @Autowired
    private korController korController;

    static class URLObj {

        private String url;

        public URLObj(String url){
           this.url = url;
        }

        public String getURL(){
           return this.url;
        }
   }

   static class ScanResponse {

        private URLObj data;
        private String message;
        private String status;

        public ScanResponse(String status, String msg, String url) {
            this.message = msg;
            this.status = status;
            this.data = new URLObj(url);
        }

        public String getMessage(){
            return this.message;
        }
        public URLObj getData(){
            return this.data;
        }
        public String getStatus(){
            return this.status;
        }
    }

    @PostMapping("/generateReport")
    public ResponseEntity<ScanResponse> triggerCreateEnv() {
        try {
            
            ResponseEntity<String> result = jenkinsOps.triggerJenkinsJob(createReportPipeline);
            if (result.getStatusCode() == HttpStatus.OK && result.getBody().equalsIgnoreCase("success")) {
            	int buildNumber = Integer.valueOf(result.getBody());
                String url = korController.getReportUrl(buildNumber);
                ScanResponse response = new ScanResponse("SUCCESS", "Scanning is completed", url);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ScanResponse("Failed","Errors while generating vulnerability reports " , null));
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
