package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class alertDetails {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	private String alertname;
	
	private String category;
	
	private String logSignature;
	
	private String remediation;

	public alertDetails(String alertname, String category, String logSignature, String remediation) {
		super();
		this.alertname = alertname;
		this.category = category;
		this.logSignature = logSignature;
		this.remediation = remediation;
	}

	public alertDetails(int id, String alertname, String category, String logSignature, String remediation) {
		super();
		this.id = id;
		this.alertname = alertname;
		this.category = category;
		this.logSignature = logSignature;
		this.remediation = remediation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAlertname() {
		return alertname;
	}

	public void setAlertname(String alertname) {
		this.alertname = alertname;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLogSignature() {
		return logSignature;
	}

	public void setLogSignature(String logSignature) {
		this.logSignature = logSignature;
	}

	public String getRemediation() {
		return remediation;
	}

	public void setRemediation(String remediation) {
		this.remediation = remediation;
	}

	@Override
	public String toString() {
		return "alertDetails [id=" + id + ", alertname=" + alertname + ", category=" + category + ", logSignature="
				+ logSignature + ", remediation=" + remediation + "]";
	}
	
	
}
