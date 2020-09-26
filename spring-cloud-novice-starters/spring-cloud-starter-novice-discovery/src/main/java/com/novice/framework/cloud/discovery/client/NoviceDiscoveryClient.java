package com.novice.framework.cloud.discovery.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.novice.framework.cloud.commons.client.NoviceServiceInstance;
import com.novice.framework.cloud.commons.support.RestTemplateHelper;
import com.novice.framework.cloud.discovery.NoviceDiscoveryProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class NoviceDiscoveryClient implements DiscoveryClient {
	private final NoviceDiscoveryProperties discoveryProperties;

	@Override
	public String description() {
		return "Spring Cloud Novice Discovery Client";
	}

	@Override
	public List<ServiceInstance> getInstances(String serviceId) {
		String discoveryInstancesUrl = this.discoveryProperties.getServerAddr() + "/discovery/" + serviceId + "/instances";
		List<NoviceServiceInstance> instances = RestTemplateHelper.getForTypeReference(discoveryInstancesUrl, new TypeReference<>() {
		});
		if (instances == null) {
			return Collections.emptyList();
		}
		return instances.stream().map(i -> (ServiceInstance) i).collect(Collectors.toList());
	}

	@Override
	public List<String> getServices() {
		String discoveryServicesUrl = this.discoveryProperties.getServerAddr() + "/discovery/services";
		List<String> services = RestTemplateHelper.getForGeneric(discoveryServicesUrl);
		return Optional.ofNullable(services).orElse(Collections.emptyList());
	}
}
