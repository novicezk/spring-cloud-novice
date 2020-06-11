/*
 * Copyright 2004-2020 Homolo Co., Ltd. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */
package com.novice.framework.cloud.discovery.registry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.web.client.RestTemplate;

public class NoviceServiceRegistry implements ServiceRegistry<NoviceRegistration> {
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void register(NoviceRegistration registration) {
		this.restTemplate.put(registration.getServerAddr() + "/registry/register", registration);
	}

	@Override
	public void deregister(NoviceRegistration registration) {
		System.out.println("deregister.......");
	}

	@Override
	public void close() {
		System.out.println("close.......");
	}

	@Override
	public void setStatus(NoviceRegistration registration, String status) {
		System.out.println("setStatus.......");
	}

	@Override
	public <T> T getStatus(NoviceRegistration registration) {
		System.out.println("getStatus.......");
		return null;
	}
}
