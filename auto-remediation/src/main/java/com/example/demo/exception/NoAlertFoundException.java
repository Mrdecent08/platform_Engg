package com.example.demo.exception;

public class NoAlertFoundException extends RuntimeException {
 
	private static final long serialVersionUID = 1L;
	private String message;
 
    public NoAlertFoundException() {}
 
    public NoAlertFoundException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}