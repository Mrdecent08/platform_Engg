package com.example.demo;

import java.util.Date;

public class ErrorMsg {

	private Integer statusCode;
	private String errorDesc;
	private Date date;

	public ErrorMsg(Integer statusCode, String errorDesc, Date date) {
		super();
		this.statusCode = statusCode;
		this.errorDesc = errorDesc;
		this.date = date;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "ErrorMsg [statusCode=" + statusCode + ", errorDesc=" + errorDesc + ", date=" + date + "]";
	}

}
