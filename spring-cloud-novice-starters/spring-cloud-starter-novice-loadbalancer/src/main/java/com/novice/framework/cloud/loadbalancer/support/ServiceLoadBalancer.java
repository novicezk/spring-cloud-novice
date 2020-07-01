/*
 * Copyright 2004-2020 Homolo Co., Ltd. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */
package com.novice.framework.cloud.loadbalancer.support;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class ServiceLoadBalancer {
	private final String serviceName;
	private final DiscoveryClient discoveryClient;
	private final List<ServiceInstance> instances = Collections.synchronizedList(new ArrayList<>());
	private final Map<String, InstanceStats> instanceStatsMap = Collections.synchronizedMap(new HashMap<>());

	public ServiceLoadBalancer(String serviceName, DiscoveryClient discoveryClient) {
		this.serviceName = serviceName;
		this.discoveryClient = discoveryClient;
		initInstances();
	}

	public void initInstances() {
		this.instances.clear();
		this.instances.addAll(this.discoveryClient.getInstances(this.serviceName));
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				syncInstances();
			}
		}, 30000L);
	}

	private void syncInstances() {
		List<ServiceInstance> newInstances = this.discoveryClient.getInstances(this.serviceName);
		Set<String> newIds = newInstances.stream().map(ServiceInstance::getInstanceId).collect(Collectors.toSet());
		Set<String> oldIds = this.instances.stream().map(ServiceInstance::getInstanceId).collect(Collectors.toSet());
		this.instances.removeIf(i -> !newIds.contains(i.getInstanceId()));
		newInstances.stream().filter(i -> !oldIds.contains(i.getInstanceId())).forEach(this.instances::add);
	}

}
