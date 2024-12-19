package com.example.demo.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Budgets;
import com.example.demo.entity.Users;



@Service
public interface userService {

	List<Users> getAllUsers();

	Users save(Users user);

	boolean checkAvailability(String username,String projectName,double tokens);
	
	Users findUserByName(String username,String projectName);

	Users updateUser(Users user);
}
