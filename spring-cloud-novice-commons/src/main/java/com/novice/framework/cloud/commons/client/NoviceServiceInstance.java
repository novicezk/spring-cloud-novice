package com.novice.framework.cloud.commons.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;

@Data
public class NoviceServiceInstance implements ServiceInstance, Serializable {
	private static final long serialVersionUID = -8293962525932403021L;

	private String instanceId;
	private String scheme;
	private String serviceId;
	private String host;
	private String status;
	private int port;
	private boolean secure;
	private Map<String, String> metadata;

	@Override
	@JsonIgnore
	public URI getUri() {
		return DefaultServiceInstance.getUri(this);
	}

}
