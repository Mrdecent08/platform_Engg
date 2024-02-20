package com.example.demo.entity;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="registerCs")
public class registerDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	private String applicationName;
		
	private String[] services;
	
	private Date startDate;
	
	private Date endDate;
	
	private String token;

	public registerDetails() {
		super();
	}

	public registerDetails(String applicationName, String[] services, Date startDate, Date endDate) {
		super();
		this.applicationName = applicationName;
		this.services = services;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public registerDetails(String applicationName, String[] services, Date startDate, Date endDate, String token) {
		super();
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

	public String[] getServices() {
		return services;
	}

	public void setServices(String[] services) {
		this.services = services;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
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
		return "registerDetails [id=" + id + ", applicationName=" + applicationName + ", services="
				+ Arrays.toString(services) + ", startDate=" + startDate + ", endDate=" + endDate + ", token=" + token
				+ "]";
	}

	
}
