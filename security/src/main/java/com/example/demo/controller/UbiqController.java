package com.example.demo.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Payload;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqsecurity.UbiqCredentials;
import com.ubiqsecurity.UbiqDecrypt;
import com.ubiqsecurity.UbiqEncrypt;
import com.ubiqsecurity.UbiqFactory;


@RestController
@RequestMapping("/data-encrypt")

public class UbiqController {

	private String accessKey = "amL0jP38UzuRuvmkUcL1rCY8";
	
	private String secretKey = "51PJ4K+mjeE0fhbkTh61jLy7Vk6x+mAI0gF4mLcBcts7";
	
	private String cryptoKey = "z5YnskHcsl9IIpK81VX0NefzkTqWo/CvrinF1/vhHzYR";

	private UbiqCredentials credentials = UbiqFactory.createCredentials(accessKey, secretKey, cryptoKey, null);

	
	@PostMapping("/encrypt")
	public String encrypt(@RequestBody Payload data) throws IOException, IllegalStateException, InvalidCipherTextException{
		byte[] plainbytes = data.toString().getBytes();
		byte[] encryptedBytes = UbiqEncrypt.encrypt(credentials, plainbytes);
		String encryptedBase64 = Base64.getEncoder().encodeToString(encryptedBytes);
		System.out.println(data.toString());
        return encryptedBase64;
	}
	
	
	@PostMapping("/decrypt")
	public String decrypt(@RequestBody String data) throws IllegalStateException, InvalidCipherTextException, IOException, ClassNotFoundException {
		byte[] encryptedBytes = Base64.getDecoder().decode(data);
		byte[] plainBytes = UbiqDecrypt.decrypt(credentials, encryptedBytes);
		String decrypted_String = new String(plainBytes, StandardCharsets.UTF_8);
		System.out.println(decrypted_String);
		return decrypted_String;
	}
	
}




