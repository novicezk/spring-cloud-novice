package com.novice.example.cloud.service.controller;

import com.novice.example.cloud.service.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
	@Autowired
	private TestService testService;

	@GetMapping("/hello")
	public String hello() {
		return this.testService.test();
	}
}
