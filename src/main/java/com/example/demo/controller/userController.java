package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.repository.userRepository;

@RestController
public class userController {

	@Autowired
	private userRepository userRepository;

	@GetMapping("/getDetails/{projectName}")
	public ResponseEntity<Object> getUserDetails(@PathVariable String projectName,
			@RequestParam(required = false) Integer userId, @RequestHeader("requestId") String requestId,
			@RequestHeader("franchise") String franchise) {

		if (!"ROGERS".equals(franchise) && !"FIDO".equals(franchise)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(errorResponse("BADREQUEST", "Invalid franchise header"));
		}

		// Fetch user data (dummy data or retrieve from the repository if saved)
		User user = userRepository.findByUserId("1234"); // Replace with actual logic
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		return ResponseEntity.ok(user);
	}

	@PostMapping("/createDetails")
	public ResponseEntity<Object> createUserDetails(@RequestHeader("requestId") String requestId,
			@RequestHeader("franchise") String franchise, @RequestBody User userDetails) {

		if (!"ROGERS".equals(franchise) && !"FIDO".equals(franchise)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(errorResponse("BADREQUEST", "Invalid franchise header"));
		}

		// Save the user and project details
		userRepository.save(userDetails);

		Map<String, Object> response = new HashMap<>();
		response.put("status", "created");
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	private Map<String, Object> errorResponse(String errorCode, String errorMessage) {
		return Map.of("serviceName", "citsel-user-details-ms", "errorCode", errorCode, "errors",
				new String[] { errorMessage });
	}
}