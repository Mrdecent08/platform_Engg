package com.example.demo.exception;

public class TokenLimitExceeded extends RuntimeException {
 
	private static final long serialVersionUID = 1L;
	private String message;
 
    public TokenLimitExceeded() {}
 
    public TokenLimitExceeded(String msg)
    {
        super(msg);
        this.message = msg;
    }
}