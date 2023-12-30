package com.main.catchy.services;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.persistence.Transient;

@Service
public class CallServicesimp {

	@Transient
	@Value("${url.notification}")
	private String URL_NOTIF_SERVICES;
	@Transient
	@Value("${message.notification}")
	private String message;
	private final RestTemplate restTemplate;

	public CallServicesimp(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Async("threadPoolTaskExecutor")
	public CompletableFuture<String> callDoctor(Object callBody)
			throws InterruptedException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		String id = null;
		int code = 0;
		String newMessage = new String(message);

		id = "notificationID";
		newMessage = newMessage.replaceAll("-N-", "menteeName");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject personJsonObject = new JSONObject();
		personJsonObject.put("notifid", id);
		personJsonObject.put("message", newMessage);

		personJsonObject.put("code", code);
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> notifications = new HttpEntity<String>(personJsonObject.toString(), headers);
		String response = restTemplate.postForObject(URL_NOTIF_SERVICES, notifications, String.class);
		Thread.sleep(1000L);
		return CompletableFuture.completedFuture(response);
	}

}
