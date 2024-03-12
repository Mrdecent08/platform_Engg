package com.example.demo.entity;

public class Payload {

	private String key;
	
	private String applicationName;

	public Payload() {
		super();
	}

	public Payload(String key, String applicationName) {
		super();
		this.key = key;
		this.applicationName = applicationName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getapplicationName() {
		return applicationName;
	}

	public void setapplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	@Override
	public String toString() {
		return "{ \"key\":\"" + key + "\", \"applicationName\":\"" + applicationName + "\"}";
	}
	
	
	
}
