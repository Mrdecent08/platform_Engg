package com.example.demo.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Budgets;
import com.example.demo.entity.Users;
import com.example.demo.exception.NoProjectFoundException;
import com.example.demo.exception.NoUserFoundException;
import com.example.demo.exception.TokenLimitExceeded;
import com.example.demo.repository.tokenizerRepository;
import com.example.demo.repository.userRepository;
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
		if(budget.get().getRemainingTokens() >= user.getTokenLimit()) {
			double newLimit = budget.get().getRemainingTokens() - user.getTokenLimit();
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
		Optional<Budgets> budget = tokenizerRepository.findByProjectName(user.getProjectName());
		if(currUser.isEmpty()) {
			throw new NoUserFoundException("No User Exists");
		}
		if(budget.get().getRemainingTokens() >= user.getTokenLimit()) {
			double newLimit = budget.get().getRemainingTokens() - user.getTokenLimit();
			budget.get().setRemainingTokens(newLimit);
			tokenizerRepository.save(budget.get());
			currUser.get().setTokenLimit(user.getTokenLimit());
			return userRepository.save(currUser.get());
		}
		else {
			throw new TokenLimitExceeded("Token Limit Exceeded !!");
		}
		
	}

	public Users findUserByName(String username,String projectName) {
		Optional<Users> user = userRepository.findByUsernameAndProjectName(username,projectName);
		if(user.isEmpty())
			throw new NoUserFoundException("No User With UserName " + username + " in Project " + projectName );
		return user.get();
	}
	
	public boolean checkAvailability(String username,String projectName,double tokens) {
		Optional<Users> user = userRepository.findByUsernameAndProjectName(username,projectName);
		double newLimit = 0;
		if(user.isEmpty())
			throw new NoUserFoundException("No User With UserName " + username + "in Project " + projectName );
		if(tokens <= user.get().getTokenLimit()-user.get().getConsumed()) {
			newLimit = user.get().getConsumed() + tokens;
			user.get().setConsumed(newLimit);
			userRepository.save(user.get());
			return true;
		}
		return false;
	}

	
	
}
	
	
