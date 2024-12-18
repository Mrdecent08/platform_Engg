package com.example.demo.exception;

public class FailedToExecuteTokens extends RuntimeException {
 
	private static final long serialVersionUID = 1L;
	private String message;
 
    public FailedToExecuteTokens() {}
 
    public FailedToExecuteTokens(String msg)
    {
        super(msg);
        this.message = msg;
    }
}