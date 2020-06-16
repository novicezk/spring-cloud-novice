package com.novice.example.cloud.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/test")
public class TestController {
	private final RestTemplate restTemplate;

	public TestController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@GetMapping("/hello")
	public String hello() {
		return this.restTemplate.getForObject("http://novice-example-service1/test/hello", String.class);
	}
}
