package com.example.demo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AlertService {

    public List<String> extractAlertNames(String jsonData) {
        List<String> alertNames = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            AlertResponse alertResponse = mapper.readValue(jsonData, AlertResponse.class);

            List<AlertGroup> alertGroups = alertResponse.getAlerts();
            for (AlertGroup alertGroup : alertGroups) {
                List<Alert> alerts = alertGroup.getAlerts();
                for (Alert alert : alerts) {
                    alertNames.add(alert.getLabels().getAlertname());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return alertNames;
    }
}
