package com.novice.framework.cloud.discovery.registry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.http.HttpMethod;
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
		String deregisterUrl = registration.getServerAddr() + "/registry/deregister/{serviceName}/{instanceId}";
		this.restTemplate.delete(deregisterUrl, registration.getServiceId(), registration.getInstanceId());
	}

	@Override
	public void close() {
		// do nothing
	}

	@Override
	public void setStatus(NoviceRegistration registration, String status) {
		String setStatusUrl = registration.getServerAddr() + "/registry/{serviceName}/{instanceId}/setStatus/{status}";
		this.restTemplate.execute(setStatusUrl, HttpMethod.POST, null, null, registration.getServiceId(), registration.getInstanceId(), status);
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getStatus(NoviceRegistration registration) {
		String getStatusUrl = registration.getServerAddr() + "/registry/{serviceName}/{instanceId}/status";
		return this.restTemplate.getForObject(getStatusUrl, String.class, registration.getServiceId(), registration.getInstanceId());
	}

}
