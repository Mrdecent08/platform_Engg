package com.example.demo.service.serviceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.Budgets;
import com.example.demo.entity.Users;
import com.example.demo.exception.FailedToExecuteTokens;
import com.example.demo.exception.NoProjectFoundException;
import com.example.demo.exception.NoUserFoundException;
import com.example.demo.exception.TokenLimitExceeded;
import com.example.demo.repository.tokenizerRepository;
import com.example.demo.repository.userRepository;
import com.example.demo.service.tokenizerService;
import com.example.demo.service.userService;

@Service
public class userServiceImpl implements userService{
	
	private userRepository userRepository;

	private tokenizerRepository tokenizerRepository;
	
		public userServiceImpl(com.example.demo.repository.userRepository userRepository,
			com.example.demo.repository.tokenizerRepository tokenizerRepository) {
		super();
		this.userRepository = userRepository;
		this.tokenizerRepository = tokenizerRepository;
	}

	@Override
	public List<Users> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Users save(Users user) {
		Optional<Users> currUser = userRepository.findByUsername(user.getUsername());
		if(!currUser.isEmpty()) {
			throw new NoUserFoundException(" User Already Exists");
		}
		Optional<Budgets> budget = tokenizerRepository.findByProjectName(user.getProjectName());
		if(budget.isEmpty()) {
			throw new NoProjectFoundException("No Project with " + user.getProjectName());
		}
		if(budget.get().getRemainingTokens() >= user.getLimit()) {
			double newLimit = budget.get().getRemainingTokens() - user.getLimit();
			budget.get().setRemainingTokens(newLimit);
			tokenizerRepository.save(budget.get());
			return userRepository.save(user);
		}
		else {
			throw new TokenLimitExceeded("Token Limit Exceeded !!");
		}
	}
	
	@Override
	public Users updateUser(Users user) {
		Optional<Users> currUser = userRepository.findByUsername(user.getUsername());
		if(currUser.isEmpty()) {
			throw new NoUserFoundException("No User Exists");
		}
		currUser.get().setLimit(user.getLimit());
		return userRepository.save(currUser.get());
	}

	public Users findUserByName(String username,String projectName) {
		Optional<Users> user = userRepository.findByUsernameAndProjectName(username,projectName);
		if(user.isEmpty())
			throw new NoUserFoundException("No User With UserName " + username + "in Project " + projectName );
		return user.get();
	}
	
	public boolean checkAvailability(String username,String projectName,double tokens) {
		Optional<Users> user = userRepository.findByUsernameAndProjectName(username,projectName);
		double newLimit = 0;
		if(user.isEmpty())
			throw new NoUserFoundException("No User With UserName " + username + "in Project " + projectName );
		if(tokens <= user.get().getLimit()-user.get().getConsumed()) {
			newLimit = user.get().getConsumed() + tokens;
			user.get().setConsumed(newLimit);
			userRepository.save(user.get());
			return true;
		}
		return false;
	}

	
	
}
	
	
