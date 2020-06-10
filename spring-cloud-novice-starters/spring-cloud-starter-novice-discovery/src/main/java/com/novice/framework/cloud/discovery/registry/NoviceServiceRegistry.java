/*
 * Copyright 2004-2020 Homolo Co., Ltd. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */
package com.novice.framework.cloud.discovery.registry;

import org.springframework.cloud.client.serviceregistry.ServiceRegistry;

public class NoviceServiceRegistry implements ServiceRegistry<NoviceRegistration> {

	@Override
	public void register(NoviceRegistration registration) {
		System.out.println("register.......");
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
