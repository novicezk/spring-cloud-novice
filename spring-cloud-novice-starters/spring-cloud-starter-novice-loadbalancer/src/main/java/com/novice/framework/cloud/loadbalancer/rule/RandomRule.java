package com.novice.framework.cloud.loadbalancer.rule;

import com.novice.framework.cloud.loadbalancer.support.ServiceLoadBalancer;
import org.springframework.cloud.client.ServiceInstance;

import java.util.List;
import java.util.Random;

public class RandomRule implements IRule {

	@Override
	public ServiceInstance choose(ServiceLoadBalancer lb) {
		List<ServiceInstance> instances = lb.getAllInstances();
		return instances.get(new Random().nextInt(instances.size()));
	}
}
