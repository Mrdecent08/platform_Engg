package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class korReport {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	private String timestamp;
	
	private String url;

	public korReport() {
		super();
	}

	public korReport(String timestamp, String url) {
		super();
		this.timestamp = timestamp;
		this.url = url;
	}

	public korReport(int id, String timestamp, String url) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "kor_report [id=" + id + ", timestamp=" + timestamp + ", url=" + url + "]";
	}
	
	
	
}
