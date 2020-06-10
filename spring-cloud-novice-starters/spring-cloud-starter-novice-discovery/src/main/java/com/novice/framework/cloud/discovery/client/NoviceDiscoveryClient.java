/*
 * Copyright 2004-2020 Homolo Co., Ltd. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */
package com.novice.framework.cloud.discovery.client;

import com.novice.framework.cloud.discovery.NoviceDiscoveryProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

@RequiredArgsConstructor
public class NoviceDiscoveryClient implements DiscoveryClient {
	private final NoviceDiscoveryProperties discoveryProperties;

	@Override
	public String description() {
		return "Spring Cloud Simple Discovery Client";
	}

	@Override
	public List<ServiceInstance> getInstances(String serviceId) {
		return null;
	}

	@Override
	public List<String> getServices() {
		return null;
	}
}
