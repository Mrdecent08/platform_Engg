package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String username;
	
	private double limit;
	
	private double consumed;

	private String projectName;

	public Users() {
		super();
	}

	
	public Users(String username, double limit, double consumed, String projectName) {
		super();
		this.username = username;
		this.limit = limit;
		this.consumed = consumed;
		this.projectName = projectName;
	}


	public Users(int id, String username, double limit, double consumed, String projectName) {
		super();
		this.id = id;
		this.username = username;
		this.limit = limit;
		this.consumed = consumed;
		this.projectName = projectName;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public double getLimit() {
		return limit;
	}


	public void setLimit(double limit) {
		this.limit = limit;
	}


	public double getConsumed() {
		return consumed;
	}


	public void setConsumed(double consumed) {
		this.consumed = consumed;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", limit=" + limit + ", consumed=" + consumed
				+ ", projectName=" + projectName + "]";
	}
	
	
	
}
