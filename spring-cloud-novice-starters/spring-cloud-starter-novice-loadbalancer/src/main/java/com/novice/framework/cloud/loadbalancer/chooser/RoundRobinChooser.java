package com.novice.framework.cloud.loadbalancer.chooser;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinChooser implements Chooser {
	private final AtomicInteger position;

	public RoundRobinChooser() {
		this.position = new AtomicInteger(0);
	}

	@Override
	public ServiceInstance choose(List<ServiceInstance> instances) {
		int pos = Math.abs(this.position.incrementAndGet());
		return instances.get(pos % instances.size());
	}
}
