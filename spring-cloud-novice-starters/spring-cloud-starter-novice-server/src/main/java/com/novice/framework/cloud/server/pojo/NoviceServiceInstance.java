/*
 * Copyright 2004-2020 Homolo Co., Ltd. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */
package com.novice.framework.cloud.server.pojo;

import lombok.Data;
import org.springframework.cloud.client.ServiceInstance;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;

@Data
public class NoviceServiceInstance implements ServiceInstance, Serializable {
	private static final long serialVersionUID = -8293962525932403021L;

	private String instanceId;
	private String serviceId;
	private String host;
	private int port;
	private boolean secure;
	private Map<String, String> metadata;

	@Override
	public URI getUri() {
		String scheme = this.isSecure() ? "https" : "http";
		String uri = String.format("%s://%s:%s", scheme, this.getHost(), this.getPort());
		return URI.create(uri);
	}

}
