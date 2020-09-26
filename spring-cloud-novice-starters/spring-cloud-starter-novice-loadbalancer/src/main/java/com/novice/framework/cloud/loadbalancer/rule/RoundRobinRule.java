package com.novice.framework.cloud.loadbalancer.rule;

import com.novice.framework.cloud.loadbalancer.support.ServiceLoadBalancer;
import org.springframework.cloud.client.ServiceInstance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinRule implements IRule {
	private final AtomicInteger position = new AtomicInteger(0);

	@Override
	public ServiceInstance choose(ServiceLoadBalancer lb) {
		List<ServiceInstance> instances = lb.getAllInstances();
		int pos = Math.abs(this.position.incrementAndGet());
		return instances.get(pos % instances.size());
	}
}
