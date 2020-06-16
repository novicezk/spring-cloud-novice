package com.novice.framework.cloud.loadbalancer.chooser;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;
import java.util.Random;

public class RandomChooser implements Chooser {

	@Override
	public ServiceInstance choose(List<ServiceInstance> instances) {
		return instances.get(new Random().nextInt(instances.size()));
	}
}
