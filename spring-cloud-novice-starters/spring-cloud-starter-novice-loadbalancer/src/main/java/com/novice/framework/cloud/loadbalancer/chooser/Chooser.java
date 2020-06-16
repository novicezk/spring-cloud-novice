package com.novice.framework.cloud.loadbalancer.chooser;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface Chooser {

	ServiceInstance choose(List<ServiceInstance> instances);
}
