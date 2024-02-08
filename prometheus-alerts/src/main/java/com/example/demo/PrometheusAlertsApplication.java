package com.example.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class PrometheusAlertsApplication {

        private RestTemplate restTemplate = new RestTemplate();

//      private String alertmanager_host="alertmanager.grafana.svc.cluster.local";
//      private String alertmanager_port="9093";

        @PostMapping("/alertHook")
        public void receiveAlertHook(@RequestBody Map<String,Object> request) throws Exception {
//              System.out.println(request);
                System.out.println(request.get("alerts"));
        }

        @GetMapping("/getAlerts")
        public String receiveAllAlerts() {
//              String alertManagerUrl = "http://"+alertmanager_host+":"+alertmanager_port+"/api/v2/alerts/groups";
                String alertManagerUrl = "http://10.63.33.181:30279/api/v2/alerts/groups";
                ResponseEntity<String> response = restTemplate.getForEntity(alertManagerUrl, String.class);
                return response.getBody();
        }

        @GetMapping("/alertsSummary")
        public HashMap<String, Object> SummaryOfAlerts() throws JsonMappingException, JsonProcessingException {
//              String alertManagerUrl = "http://"+alertmanager_host+":"+alertmanager_port+"/api/v2/alerts/groups";
                String alertManagerUrl = "http://10.63.33.181:30279/api/v2/alerts/groups";
                String response = restTemplate.getForEntity(alertManagerUrl, String.class).getBody();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode objectNode = mapper.readTree(response);
                int totalAlerts = 0;
                HashMap<String, Integer> alertsMap = new HashMap<>();
                for(int i=0;i<objectNode.size();i++) {
                        String alertName = objectNode.get(i).get("labels").get("alertname").asText();
                        int noOfAlerts= objectNode.get(i).get("alerts").size();
                        totalAlerts += noOfAlerts;
                        alertsMap.put(alertName, noOfAlerts);
                }
                HashMap<String,Object> requestResponse = new HashMap<>();
                requestResponse.put("totalAlerts",totalAlerts);
                requestResponse.put("alerts", alertsMap);
                return requestResponse;
        }

        public static void main(String[] args) {
                SpringApplication.run(PrometheusAlertsApplication.class, args);
        }

}