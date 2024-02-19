package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

@Entity
@Table(name="registerCs")
public class registerDetails {

	@Id
	private int id;
	
	private String applicationName;
	
	private String services;
	
	private String startDate;
	
	private String endDate;
	
	private String token;

	public registerDetails(int id, String applicationName, String services, String startDate, String endDate) {
		super();
		this.id = id;
		this.applicationName = applicationName;
		this.services = services;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public registerDetails(int id, String applicationName, String services, String startDate, String endDate,
			String token) {
		super();
		this.id = id;
		this.applicationName = applicationName;
		this.services = services;
		this.startDate = startDate;
		this.endDate = endDate;
		this.token = token;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "registerDetails [id=" + id + ", applicationName=" + applicationName + ", services=" + services
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", token=" + token + "]";
	}
	
	
	
	
}
