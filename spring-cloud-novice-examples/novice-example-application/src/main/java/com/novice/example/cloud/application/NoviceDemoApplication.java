package com.novice.example.cloud.application;

import com.novice.framework.cloud.discovery.client.NoviceDiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableDiscoveryClient
public class NoviceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoviceDemoApplication.class, args);
	}

	@Autowired
	private NoviceDiscoveryClient discoveryClient;

	@EventListener(InstanceRegisteredEvent.class)
	public void registered() {
		System.out.println("instance registered...");
		System.out.println(this.discoveryClient.description());
		System.out.println(this.discoveryClient.getServices());
		System.out.println(this.discoveryClient.getInstances("novice-example-application"));
	}

}
