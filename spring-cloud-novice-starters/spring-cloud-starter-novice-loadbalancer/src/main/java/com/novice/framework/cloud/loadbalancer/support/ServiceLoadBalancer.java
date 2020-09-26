package com.novice.framework.cloud.loadbalancer.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class ServiceLoadBalancer {
	private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

	private final String serviceName;
	private final DiscoveryClient discoveryClient;
	private final List<ServiceInstance> instances = Collections.synchronizedList(new ArrayList<>());
	private final Map<String, InstanceStats> instanceStatsMap = Collections.synchronizedMap(new HashMap<>());

	public ServiceLoadBalancer(String serviceName, DiscoveryClient discoveryClient) {
		this.serviceName = serviceName;
		this.discoveryClient = discoveryClient;
		this.instances.addAll(this.discoveryClient.getInstances(this.serviceName));
		this.scheduledExecutorService.scheduleAtFixedRate(this::syncInstanceList, 30, 30, TimeUnit.SECONDS);
	}

	public List<ServiceInstance> getAllInstances() {
		return Collections.unmodifiableList(this.instances);
	}

	public InstanceStats getInstanceStats(ServiceInstance serviceInstance) {
		return this.instanceStatsMap.computeIfAbsent(serviceInstance.getInstanceId(), s -> new InstanceStats(serviceInstance));
	}

	public <T> T execute(ServiceInstance serviceInstance, LoadBalancerRequest<T> request) throws Exception {
		InstanceStats stats = getInstanceStats(serviceInstance);
		long startTime = System.currentTimeMillis();
		stats.incrementActiveRequestsCount();
		try {
			T apply = request.apply(serviceInstance);
			stats.addSuccessRequest(startTime);
			return apply;
		} catch (Exception e) {
			stats.addFailureRequest(startTime);
			throw e;
		}
	}

	private void syncInstanceList() {
		long currentTime = System.currentTimeMillis();
		List<ServiceInstance> newInstances = this.discoveryClient.getInstances(this.serviceName);
		Set<String> newIds = newInstances.stream().map(ServiceInstance::getInstanceId).collect(Collectors.toSet());
		Set<String> oldIds = this.instances.stream().map(ServiceInstance::getInstanceId).collect(Collectors.toSet());
		this.instances.removeIf(i -> {
			if (!newIds.contains(i.getInstanceId())) {
				this.instanceStatsMap.remove(i.getInstanceId());
				return true;
			}
			return false;
		});
		newInstances.stream().filter(i -> !oldIds.contains(i.getInstanceId())).forEach(this.instances::add);
		log.debug("sync service[{}]'s instances success, cost {}ms", this.serviceName, System.currentTimeMillis() - currentTime);
	}
}
