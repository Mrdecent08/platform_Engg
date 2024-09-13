package com.example.demo.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@Component
public class JenkinsOps {

    @Value("${jenkins.url}")
    private String jenkinsUrl;

    @Value("${jenkins.token}")
    private String jenkinsToken;

    @Value("${jenkins.user}")
    private String jenkinsUser;

    public ResponseEntity<String> triggerJenkinsJob(String jenkinsJob){

        try{

            String apiUrl = jenkinsUrl + "/job/" + jenkinsJob + "/buildWithParameters";
            String authHeaderValue = "Basic " + Base64.getEncoder().encodeToString((jenkinsUser + ":" + jenkinsToken).getBytes());

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeaderValue);

            // Create a RestTemplate and send the POST request to trigger the Jenkins job with parameters
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

            if (responseEntity.getStatusCode() == HttpStatus.CREATED) {

                String locationHeader = responseEntity.getHeaders().getFirst("Location");
                // Poll for job completion and get the build number
                String buildNumber = pollForJobCompletionAndRetrieveBuildNumber(locationHeader,authHeaderValue);
                String result = waitForJobCompletion(jenkinsUrl,jenkinsJob,buildNumber, authHeaderValue);

                return new ResponseEntity<>(buildNumber, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to trigger Jenkins job", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Exception to trigger Jenkins job ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String extractJobStatusFromResponse(String responseBody) {
        try {
            // Parse the JSON response using Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            // Extract the "result" field
            String jobStatus = jsonNode.get("result").asText();

            // Return the extracted job status
            return jobStatus;
        } catch (Exception e) {
            // Handle any parsing exceptions here (e.g., JSON parsing error)
            e.printStackTrace();
            return "UNKNOWN";
        }
    }

    public String waitForJobCompletion(String jenkinsUrl,String jenkinsJob ,String buildNumber,String authHeaderValue) {
        try {
            int maxAttempts = 100; // Maximum number of polling attempts
            int pollingIntervalSeconds = 5; // Interval between polling attempts (in seconds)

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeaderValue);

            for (int attempt = 1; attempt <= maxAttempts; attempt++) {
                // Make a GET request to the Jenkins API with Basic Authentication
                String apiUrl = jenkinsUrl + "/job/" + jenkinsJob +"/"+buildNumber+"/"+ "api/json";
                System.out.println("jobURL : " + apiUrl);

                // Set the headers with Basic Authentication
                RestTemplate restTemplate = new RestTemplate();
                HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(headers);
                ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    // Parse the Jenkins JSON response to extract the build number
                    String jsonResponse = responseEntity.getBody();
                    String jobStatus = extractJobStatusFromResponse(jsonResponse);
                    System.out.println("jobstatus is : "+jobStatus);
                    if ("SUCCESS".equals(jobStatus)) {
                        // The job has completed successfully
                        return "SUCCESS";
                    } else if ("FAILURE".equals(jobStatus)) {
                        // The job has completed with a failure
                        return "FAILURE";
                    }
                }
                Thread.sleep(pollingIntervalSeconds * 1000);
            }

            // If the job didn't complete within the maximum attempts, return an "UNKNOWN" build number
            return "UNKNOWN";
        } catch (Exception e) {
            // Handle exceptions and return an "UNKNOWN" build number in case of errors
            System.out.print("exception: " + e);
            return "UNKNOWN";
        }
    }


    public String pollForJobCompletionAndRetrieveBuildNumber(String jobQueueUrl,String authHeaderValue) {
        try {
            int maxAttempts = 10; // Maximum number of polling attempts
            int pollingIntervalSeconds = 5; // Interval between polling attempts (in seconds)

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeaderValue);

            for (int attempt = 1; attempt <= maxAttempts; attempt++) {
                // Make a GET request to the Jenkins API with Basic Authentication
                String apiUrl = jobQueueUrl + "api/json";
                System.out.println("fetching buildNumber : " + apiUrl);

                // Set the headers with Basic Authentication
                RestTemplate restTemplate = new RestTemplate();
                HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(headers);
                ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    // Parse the Jenkins JSON response to extract the build number
                    String jsonResponse = responseEntity.getBody();
                    String buildNumber = extractBuildNumberFromJson(jsonResponse);

                    if (buildNumber != null) {
                        return buildNumber;
                    }
                }

                Thread.sleep(pollingIntervalSeconds * 1000);
            }

            // If the job didn't complete within the maximum attempts, return an "UNKNOWN" build number
            return "UNKNOWN";
        } catch (Exception e) {
            // Handle exceptions and return an "UNKNOWN" build number in case of errors
            System.out.print("exception: " + e);
            return "UNKNOWN";
        }
    }

    private String extractBuildNumberFromJson(String jsonResponse) {
        try {
            // Parse the JSON response using a JSON parsing library (e.g., Jackson)
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            // Check if the response contains an "executable" object
            if (jsonNode.has("executable")) {
                JsonNode executableNode = jsonNode.get("executable");

                // Extract the build number from the "number" field within the "executable" object
                if (executableNode.has("number")) {
                    int buildNumber = executableNode.get("number").asInt();
                    return String.valueOf(buildNumber);
                }
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }
}