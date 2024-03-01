package com.example.demo.service.serviceImpl;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.example.demo.entity.registerDetails;
import com.example.demo.service.userService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class userServiceImpl implements userService {

	@Override
	public String generateToken() throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody
				.create("client_id=security-admin-console&username=user&password=admin&grant_type=password", mediaType);
		Request request = new Request.Builder()
				.url("http://10.63.20.62:31537/realms/master/protocol/openid-connect/token").method("POST", body)
				.addHeader("Content-Type", "application/x-www-form-urlencoded").build();
		Response response = client.newCall(request).execute();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode objectNode = mapper.readTree(response.body().string());
		return objectNode.get("access_token").asText();
	}

	@Override
	public void createUser(registerDetails details, String token) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		StringBuilder sb = new StringBuilder();
		sb.append("{ \\\"services\\\" : [");
		for (String i : details.getServices()) {
			sb.append("\\\"" + i + "\\\",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("] }");
		RequestBody body = RequestBody.create("{\n    \"username\": \"" + details.getApplicationName()
				+ "\",\n    \"enabled\": true,\n    \"attributes\": {\n      \"microservices\": \"" + sb.toString()
				+ "\" ,\n      \"expiration_time\": \"2024-12-31T23:59:59Z\"\n    },\n    \"credentials\": [\n      {\n        \"type\": \"password\",\n        \"value\": \""
				+ details.getApplicationName() + "\",\n        \"temporary\": false\n      }\n    ]\n  }", mediaType);
		Request request = new Request.Builder().url("http://10.63.20.62:31537/admin/realms/AuthTest/users")
				.method("POST", body).addHeader("Content-Type", "application/json")
				.addHeader("Authorization", "Bearer " + token).build();
		Response response = client.newCall(request).execute();
		System.out.println("User Created Successfully !!" + response.body().string());

	}

	public String getApplicationId(String appName) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = RequestBody.create("", mediaType);
		Request request = new Request.Builder()
				.url("http://10.63.20.62:31537/admin/realms/AuthTest/users?username=" + appName).method("GET", body)
				.addHeader("Authorization", "Bearer " + generateToken()).build();
		Response response = client.newCall(request).execute();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode objectNode = mapper.readTree(response.body().string());
		return objectNode.get(0).get("id").asText();
	}

	@Override
	public void updateUser(registerDetails details, String token) throws IOException {

		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		StringBuilder sb = new StringBuilder();
		sb.append("{ \\\"services\\\" : [");
		for (String i : details.getServices()) {
			sb.append("\\\"" + i + "\\\",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("] }");
		RequestBody body = RequestBody.create("{\n    \"firstName\": \"New First Name\",\n    \"lastName\": \"New Last Name\",\n    \"attributes\": {\n        \"microservices\":\""+ sb.toString() +"\"\n    }\n}",mediaType);
		Request request = new Request.Builder()
				.url("http://10.63.20.62:31537/admin/realms/AuthTest/users/89a4cf99-cb0e-4a31-8861-428dcec6dedb")
				.method("PUT", body).addHeader("Content-Type", "application/json")
				.addHeader("Authorization",
						"Bearer "+ token)
				.build();
		Response response = client.newCall(request).execute();
		System.out.println("Update Successful");
	}

}