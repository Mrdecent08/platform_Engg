package com.example.demo.service.serviceImpl;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.example.demo.service.userService;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class userServiceImpl implements userService {

	@Override
	public void generateToken() throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create("client_id=security-admin-console&username=user&password=admin",mediaType);
		Request request = new Request.Builder()
				.url("http://10.63.20.62:31537/realms/master/protocol/openid-connect/token").method("POST", body)
				.addHeader("Content-Type", "application/x-www-form-urlencoded").build();
		Response response = client.newCall(request).execute();
		System.out.println(response);
	}

}
