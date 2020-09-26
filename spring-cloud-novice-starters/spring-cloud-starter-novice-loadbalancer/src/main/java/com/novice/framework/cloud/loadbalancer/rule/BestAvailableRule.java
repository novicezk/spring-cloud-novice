package com.novice.framework.cloud.loadbalancer.rule;

import com.novice.framework.cloud.loadbalancer.support.InstanceStats;
import com.novice.framework.cloud.loadbalancer.support.ServiceLoadBalancer;
import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public class BestAvailableRule implements IRule {

	@Override
	public ServiceInstance choose(ServiceLoadBalancer lb) {
		List<ServiceInstance> instances = lb.getAllInstances();
		int currentActiveRequestsCount = Integer.MAX_VALUE;
		ServiceInstance chosen = null;
		for (ServiceInstance instance : instances) {
			InstanceStats instanceStats = lb.getInstanceStats(instance);
			int activeRequestsCount = instanceStats.getActiveRequestsCount();
			if (activeRequestsCount < currentActiveRequestsCount) {
				currentActiveRequestsCount = activeRequestsCount;
				chosen = instance;
			}
		}
		return chosen;
	}
}
