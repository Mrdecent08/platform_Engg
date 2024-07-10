package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Metrics {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	private String alertName;
	
	private int count;

	public Metrics() {
		super();
	}

	
	public Metrics(String alertName, int count) {
		super();
		this.alertName = alertName;
		this.count = count;
	}


	public Metrics(int id, String alertName, int count) {
		super();
		this.id = id;
		this.alertName = alertName;
		this.count = count;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getAlertName() {
		return alertName;
	}


	public void setAlertName(String alertName) {
		this.alertName = alertName;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	@Override
	public String toString() {
		return "Metrics [id=" + id + ", alertName=" + alertName + ", count=" + count + "]";
	}
	
	
	
}
