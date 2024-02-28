package com.example.demo.service.serviceImpl;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.example.demo.entity.registerDetails;
import com.example.demo.service.userService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

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
		RequestBody body = RequestBody.create("client_id=security-admin-console&username=user&password=admin&grant_type=password",mediaType);
		Request request = new Request.Builder()
				.url("http://10.63.20.62:31537/realms/master/protocol/openid-connect/token").method("POST", body)
				.addHeader("Content-Type", "application/x-www-form-urlencoded").build();
		Response response = client.newCall(request).execute();
		ObjectMapper mapper = new ObjectMapper();
        JsonNode objectNode = mapper.readTree(response.body().string());
        return objectNode.get("access_token").asText();
	}

	@Override
	public void createUser(registerDetails details,String token) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				String str = "{\n    \"username\": \""+ details.getApplicationName() + "\",\n    \"enabled\": true,\n    \"attributes\": {\n      \"microservices\": \""+ details.getServices() +"\" ,\n      \"expiration_time\": \"2024-12-31T23:59:59Z\"\n    },\n    \"credentials\": [\n      {\n        \"type\": \"password\",\n        \"value\": \""+ details.getApplicationName() + "\",\n        \"temporary\": false\n      }\n    ]\n  }";
				System.out.println(str);
		        RequestBody body = RequestBody.create("{\n    \"username\": \""+ details.getApplicationName() + "\",\n    \"enabled\": true,\n    \"attributes\": {\n      \"microservices\": \""+ details.getServices() +"\" ,\n      \"expiration_time\": \"2024-12-31T23:59:59Z\"\n    },\n    \"credentials\": [\n      {\n        \"type\": \"password\",\n        \"value\": \""+ details.getApplicationName() + "\",\n        \"temporary\": false\n      }\n    ]\n  }",mediaType);
		        Request request = new Request.Builder()
				  .url("http://10.63.20.62:31537/admin/realms/AuthTest/users")
				  .method("POST", body)
				  .addHeader("Content-Type", "application/json")
				  .addHeader("Authorization", "Bearer "+ token)
				  .build();
				Response response = client.newCall(request).execute();
				System.out.println("User Created Successfully !!" + response.body().string());
		
	}

}