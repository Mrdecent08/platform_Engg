package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.userDetails;

public interface userService {

	List<userDetails> getAlluserDetails();

	userDetails saveuserDetails(userDetails userDetails);

}
