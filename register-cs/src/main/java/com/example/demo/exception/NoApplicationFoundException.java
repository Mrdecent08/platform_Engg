package com.example.demo.exception;

public class NoApplicationFoundException extends RuntimeException {
 
	private static final long serialVersionUID = 1L;
	private String message;
 
    public NoApplicationFoundException() {}
 
    public NoApplicationFoundException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}