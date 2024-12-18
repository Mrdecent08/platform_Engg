package com.example.demo.exception;

public class NoProjectFoundException extends RuntimeException {
 
	private static final long serialVersionUID = 1L;
	private String message;
 
    public NoProjectFoundException() {}
 
    public NoProjectFoundException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}