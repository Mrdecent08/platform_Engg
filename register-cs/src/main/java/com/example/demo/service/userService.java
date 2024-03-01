package com.example.demo.service;

import java.io.IOException;

import com.example.demo.entity.registerDetails;

public interface userService {

	String generateToken() throws IOException;

	void createUser(registerDetails details,String token) throws IOException;

	String getApplicationId(String appName) throws IOException;

	void updateUser(registerDetails details, String token) throws IOException;



}
