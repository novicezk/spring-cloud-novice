/*
 * Copyright 2004-2020 Homolo Co., Ltd. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */
package com.novice.framework.cloud.discovery.registry;

import org.springframework.cloud.client.serviceregistry.AbstractAutoServiceRegistration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;

public class NoviceAutoServiceRegistration extends AbstractAutoServiceRegistration<NoviceRegistration> {
	private final NoviceRegistration noviceRegistration;

	public NoviceAutoServiceRegistration(ServiceRegistry<NoviceRegistration> serviceRegistry,
										 AutoServiceRegistrationProperties properties,
										 NoviceRegistration registration) {
		super(serviceRegistry, properties);
		this.noviceRegistration = registration;
	}

	@Override
	protected boolean isEnabled() {
		return true;
	}

	@Override
	protected NoviceRegistration getRegistration() {
		return this.noviceRegistration;
	}

	@Override
	protected NoviceRegistration getManagementRegistration() {
		return null;
	}

	@Override
	@Deprecated
	protected Object getConfiguration() {
		return null;
	}
}
