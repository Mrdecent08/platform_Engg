package com.example.demo;

import java.util.List;

public class AlertResponse {
	private List<AlertGroup> alerts;
	List<AlertGroup> getAlerts(){
		return alerts;
	}
	public void setAlerts(List<AlertGroup> alerts) {
		this.alerts = alerts;
	}	
}

class AlertGroup{
	private List<Alert> alerts;

	public List<Alert> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<Alert> alerts) {
		this.alerts = alerts;
	}
}

class Alert{
	private AlertLabels labels;

	public AlertLabels getLabels() {
		return labels;
	}

	public void setLabels(AlertLabels labels) {
		this.labels = labels;
	}
	
}

class AlertLabels{
	private String alertname;

	public String getAlertname() {
		return alertname;
	}

	public void setAlertname(String alertname) {
		this.alertname = alertname;
	}
	
}