/*
 * Copyright 2004-2020 Homolo Co., Ltd. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */
package com.novice.framework.cloud.loadbalancer.support;

import org.springframework.cloud.client.ServiceInstance;

import java.util.concurrent.atomic.AtomicInteger;

public class InstanceStats {
	private final ServiceInstance serviceInstance;
	private AtomicInteger totalRequests = new AtomicInteger(0);
	private AtomicInteger activeRequestsCount = new AtomicInteger(0);

	public InstanceStats(ServiceInstance serviceInstance) {
		this.serviceInstance = serviceInstance;
	}
}
