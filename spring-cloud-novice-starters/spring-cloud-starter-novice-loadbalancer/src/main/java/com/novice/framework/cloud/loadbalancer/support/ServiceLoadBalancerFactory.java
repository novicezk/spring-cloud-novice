package com.novice.framework.cloud.loadbalancer.support;

import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ServiceLoadBalancerFactory {
	private final Map<String, ServiceLoadBalancer> serviceLoadBalancerMap = Collections.synchronizedMap(new HashMap<>());
	private final DiscoveryClient discoveryClient;

	public ServiceLoadBalancerFactory(DiscoveryClient discoveryClient) {
		this.discoveryClient = discoveryClient;
	}

	public ServiceLoadBalancer loadBalancer(String serviceName) {
		return this.serviceLoadBalancerMap.computeIfAbsent(serviceName, s -> new ServiceLoadBalancer(s, this.discoveryClient));
	}

}
