package com.example.demo.service.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.registerDetails;
import com.example.demo.exception.ApplicationExists;
import com.example.demo.exception.NoApplicationFoundException;
import com.example.demo.repository.registerDetailsRepository;
import com.example.demo.service.registerCsService;
import com.example.demo.service.userService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class registerCsServiceImpl implements registerCsService {

	private registerDetailsRepository registerRepository;
	private userService userService;

	public registerCsServiceImpl(registerDetailsRepository registerRepository, userService userService) {
		super();
		this.registerRepository = registerRepository;
		this.userService = userService;

	}

	@Override
	public String createApplication(registerDetails details) throws IOException {
		String appName = details.getApplicationName();
		Optional<registerDetails> detail = registerRepository.findByApplicationName(appName);
		if (detail.isEmpty()) {
			String token = userService.generateToken();
			userService.createUser(details, token);
			details.setToken(generateTokenForApplication(details.getApplicationName()));
			registerRepository.save(details);
			return getTokenByApplicationName(details.getApplicationName());
		} else {
			return "Application Already Exists";
		}

	}

	private String generateTokenForApplication(String applicationName) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(
				"client_id=istio&username=" + applicationName + "&password=" + applicationName + "&grant_type=password",
				mediaType);
		Request request = new Request.Builder()
				.url("http://10.63.33.181:31537/realms/AuthTest/protocol/openid-connect/token").method("POST", body)
				.addHeader("Content-Type", "application/x-www-form-urlencoded").build();
		Response response = client.newCall(request).execute();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode objectNode = mapper.readTree(response.body().string());
		return objectNode.get("access_token").asText();
	}

	@Override
	public List<registerDetails> getAllApplicationsDetails() {
		return registerRepository.findAll();
	}

	@Override
	public String updateApplication(registerDetails details) {
		String appName = details.getApplicationName();
		Optional<registerDetails> detail = registerRepository.findByApplicationName(appName);
		if (detail.isEmpty()) {
			throw new NoApplicationFoundException("No Application with Name : " + appName);
		} else {
			registerRepository.save(details);
			return "Details Are Updated Successfully";
		}

	}

	@Override
	public String deleteApplication(int id) {
		registerRepository.deleteById(id);
		return "Application Deleted Successfully";
	}

	@Override
	public Optional<registerDetails> getAllApplicationsDetailsById(int id) {
		Optional<registerDetails> details = registerRepository.findById(id);
		if (details.isEmpty()) {
			throw new NoApplicationFoundException("No Application with ID: " + id);
		} else {
			return details;
		}
	}

	@Override
	public String getTokenByApplicationName(String appName) {
		Optional<registerDetails> detail = registerRepository.findByApplicationName(appName);
		if (detail.isEmpty()) {
			throw new NoApplicationFoundException("No Application with Name : " + appName);
		} else {
			return detail.get().getToken();
		}
	}

}
