package com.novice.framework.cloud.loadbalancer.client;

import com.novice.framework.cloud.loadbalancer.chooser.Chooser;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequest;
import org.springframework.cloud.client.loadbalancer.LoadBalancerUriTools;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
public class NoviceLoadBalancerClient implements LoadBalancerClient {
	private final DiscoveryClient discoveryClient;
	private final Chooser chooser;

	@Override
	public <T> T execute(String serviceId, LoadBalancerRequest<T> request) throws IOException {
		return execute(serviceId, choose(serviceId), request);
	}

	@Override
	public <T> T execute(String serviceId, ServiceInstance serviceInstance, LoadBalancerRequest<T> request) throws IOException {
		try {
			return request.apply(serviceInstance);
		} catch (Exception e) {
			ReflectionUtils.rethrowRuntimeException(e);
		}
		return null;
	}

	@Override
	public URI reconstructURI(ServiceInstance instance, URI original) {
		return LoadBalancerUriTools.reconstructURI(instance, original);
	}

	@Override
	public ServiceInstance choose(String serviceId) {
		List<ServiceInstance> instances = this.discoveryClient.getInstances(serviceId);
		if (instances.isEmpty()) {
			throw new IllegalStateException("No instances available for " + serviceId);
		}
		return this.chooser.choose(instances);
	}
}
