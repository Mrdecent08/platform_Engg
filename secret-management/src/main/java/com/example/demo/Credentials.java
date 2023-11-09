package com.example.demo;

public class Credentials {

	private String username;
	
	private String password;

	private String applicationName;

	public Credentials() {
		super();
	}

	public Credentials(String username, String password, String applicationName) {
		super();
		this.username = username;
		this.password = password;
		this.applicationName = applicationName;
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

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	@Override
	public String toString() {
		return "Credentials [username=" + username + ", password=" + password + ", applicationName=" + applicationName
				+ "]";
	}
	
	
}
