package com.novice.framework.cloud.loadbalancer.client;

import com.novice.framework.cloud.loadbalancer.rule.IRule;
import com.novice.framework.cloud.loadbalancer.support.ServiceLoadBalancer;
import com.novice.framework.cloud.loadbalancer.support.ServiceLoadBalancerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequest;
import org.springframework.cloud.client.loadbalancer.LoadBalancerUriTools;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.net.URI;

@Slf4j
public class NoviceLoadBalancerClient implements LoadBalancerClient {
	private final ServiceLoadBalancerFactory loadBalancerFactory;
	private final IRule rule;

	public NoviceLoadBalancerClient(ServiceLoadBalancerFactory loadBalancerFactory, IRule rule) {
		this.loadBalancerFactory = loadBalancerFactory;
		this.rule = rule;
	}

	@Override
	public <T> T execute(String serviceId, LoadBalancerRequest<T> request) throws IOException {
		return execute(serviceId, choose(serviceId), request);
	}

	@Override
	public <T> T execute(String serviceId, ServiceInstance serviceInstance, LoadBalancerRequest<T> request) throws IOException {
		log.debug("service: {}, instance id: {}", serviceId, serviceInstance.getInstanceId());
		try {
			return this.loadBalancerFactory.loadBalancer(serviceId).execute(serviceInstance, request);
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			ReflectionUtils.rethrowRuntimeException(e);
			return null;
		}
	}

	@Override
	public URI reconstructURI(ServiceInstance instance, URI original) {
		return LoadBalancerUriTools.reconstructURI(instance, original);
	}

	@Override
	public ServiceInstance choose(String serviceId) {
		ServiceLoadBalancer serviceLoadBalancer = this.loadBalancerFactory.loadBalancer(serviceId);
		ServiceInstance instance = this.rule.choose(serviceLoadBalancer);
		if (instance == null) {
			throw new IllegalStateException("No instances available for " + serviceId);
		}
		return instance;
	}
}
