package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class userDetails {

	@Id
	private long id;
	private String username;
	private String password;
	public userDetails() {
		super();
	}
	
	public userDetails(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public userDetails(long id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "userDetails [id=" + id + ", username=" + username + ", password=" + password + "]";
	}	
	
	
}

