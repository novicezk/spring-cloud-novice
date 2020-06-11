/*
 * Copyright 2004-2020 Homolo Co., Ltd. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */
package com.novice.framework.cloud.discovery.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novice.framework.cloud.commons.client.NoviceServiceInstance;
import com.novice.framework.cloud.commons.exception.ValidationException;
import com.novice.framework.cloud.discovery.NoviceDiscoveryProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class NoviceDiscoveryClient implements DiscoveryClient {
	private final NoviceDiscoveryProperties discoveryProperties;
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public String description() {
		return "Spring Cloud Novice Discovery Client";
	}

	@Override
	public List<ServiceInstance> getInstances(String serviceId) {
		String discoveryInstancesUrl = this.discoveryProperties.getServerAddr() + "/discovery/" + serviceId + "/instances";
		var result = this.restTemplate.getForObject(discoveryInstancesUrl, String.class);
		try {
			List<NoviceServiceInstance> instances = new ObjectMapper().readValue(result, new TypeReference<>() {
			});
			return new ArrayList<>(instances);
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

	@Override
	public List<String> getServices() {
		String discoveryServicesUrl = this.discoveryProperties.getServerAddr() + "/discovery/services";
		var result = this.restTemplate.getForObject(discoveryServicesUrl, String.class);
		try {
			return new ObjectMapper().readValue(result, new TypeReference<>() {
			});
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}
}
