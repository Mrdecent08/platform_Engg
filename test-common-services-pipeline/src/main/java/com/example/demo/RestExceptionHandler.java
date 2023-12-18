package com.example.demo;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class RestExceptionHandler {

	@ExceptionHandler(value = NoMethodFoundException.class)
	public ResponseEntity<ErrorMsg> handleNoMethodException() {
		
		ErrorMsg error = new ErrorMsg(400,"No Such Method Found",new Date());
		return new ResponseEntity<ErrorMsg>(error,HttpStatus.BAD_REQUEST);
	}
}
