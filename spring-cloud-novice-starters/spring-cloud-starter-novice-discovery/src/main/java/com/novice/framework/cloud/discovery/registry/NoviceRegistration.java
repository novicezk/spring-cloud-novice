/*
 * Copyright 2004-2020 Homolo Co., Ltd. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */
package com.novice.framework.cloud.discovery.registry;

import com.novice.framework.cloud.discovery.NoviceDiscoveryProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.serviceregistry.Registration;

import java.net.URI;
import java.util.Map;

@RequiredArgsConstructor
public class NoviceRegistration implements Registration {
	private final NoviceDiscoveryProperties discoveryProperties;

	@Override
	public String getServiceId() {
		return this.discoveryProperties.getService();
	}

	@Override
	public String getHost() {
		return this.discoveryProperties.getHost();
	}

	@Override
	public int getPort() {
		return this.discoveryProperties.getPort();
	}

	@Override
	public boolean isSecure() {
		return this.discoveryProperties.isSecure();
	}

	@Override
	public URI getUri() {
		String scheme = this.isSecure() ? "https" : "http";
		String uri = String.format("%s://%s:%s", scheme, this.getHost(), this.getPort());
		return URI.create(uri);
	}

	@Override
	public Map<String, String> getMetadata() {
		return this.discoveryProperties.getMetadata();
	}

	public String getServerAddr() {
		return this.discoveryProperties.getServerAddr();
	}
}
