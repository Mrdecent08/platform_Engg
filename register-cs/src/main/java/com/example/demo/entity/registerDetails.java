package com.example.demo.entity;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="registerCs")
public class registerDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	private String projectName;
	
	private String applicationName;
		
	@ElementCollection
	@CollectionTable(name="cs",joinColumns = @JoinColumn(name="id"))
	private ArrayList<String> services;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String token;

	public registerDetails() {
		super();
	}

	public registerDetails(String projectName, String applicationName, ArrayList<String> services, LocalDate startDate,
			LocalDate endDate) {
		super();
		this.projectName = projectName;
		this.applicationName = applicationName;
		this.services = services;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public registerDetails(String projectName, String applicationName, ArrayList<String> services, LocalDate startDate,
			LocalDate endDate, String token) {
		super();
		this.projectName = projectName;
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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public ArrayList<String> getServices() {
		return services;
	}

	public void setServices(ArrayList<String> services) {
		this.services = services;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
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
		return "registerDetails [id=" + id + ", projectName=" + projectName + ", applicationName=" + applicationName
				+ ", services=" + services + ", startDate=" + startDate + ", endDate=" + endDate + ", token=" + token
				+ "]";
	}

	
		
}
