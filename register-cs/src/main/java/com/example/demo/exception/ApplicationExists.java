package com.example.demo.exception;

public class ApplicationExists extends RuntimeException {
 
	private static final long serialVersionUID = 1L;
	private String message;
 
    public ApplicationExists() {}
 
    public ApplicationExists(String msg)
    {
        super(msg);
        this.message = msg;
    }
}