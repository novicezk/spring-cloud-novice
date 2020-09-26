package com.novice.framework.cloud.discovery.registry;

import com.novice.framework.cloud.commons.support.RestTemplateHelper;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;

public class NoviceServiceRegistry implements ServiceRegistry<NoviceRegistration> {

	@Override
	public void register(NoviceRegistration registration) {
		RestTemplateHelper.getRestTemplate().put(registration.getServerAddr() + "/registry/register", registration);
	}

	@Override
	public void deregister(NoviceRegistration registration) {
		String deregisterUrl = registration.getServerAddr() + "/registry/deregister/{serviceName}/{instanceId}";
		RestTemplateHelper.getRestTemplate().delete(deregisterUrl, registration.getServiceId(), registration.getInstanceId());
	}

	@Override
	public void close() {
		// do nothing
	}

	@Override
	public void setStatus(NoviceRegistration registration, String status) {
		String setStatusUrl = registration.getServerAddr() + "/registry/{serviceName}/{instanceId}/setStatus/{status}";
		RestTemplateHelper.post(setStatusUrl, registration.getServiceId(), registration.getInstanceId(), status);
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getStatus(NoviceRegistration registration) {
		String getStatusUrl = registration.getServerAddr() + "/registry/{serviceName}/{instanceId}/status";
		return RestTemplateHelper.getRestTemplate().getForObject(getStatusUrl, String.class, registration.getServiceId(), registration.getInstanceId());
	}

}
