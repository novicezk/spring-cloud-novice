package com.novice.example.cloud.service;

import com.novice.framework.cloud.discovery.client.NoviceDiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableDiscoveryClient
public class NoviceService1Application {

	public static void main(String[] args) {
		SpringApplication.run(NoviceService1Application.class, args);
	}

	@Autowired
	private NoviceDiscoveryClient discoveryClient;

	@Value("${spring.application.name}")
	private String applicationName;

	@EventListener(InstanceRegisteredEvent.class)
	public void registered() {
		System.out.println("instance registered...");
		System.out.println(this.discoveryClient.getServices());
		System.out.println(this.discoveryClient.getInstances(this.applicationName));
	}

}
