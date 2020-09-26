package com.novice.framework.cloud.loadbalancer.rule;

import com.novice.framework.cloud.loadbalancer.support.ServiceLoadBalancer;
import org.springframework.cloud.client.ServiceInstance;

public interface IRule {

	ServiceInstance choose(ServiceLoadBalancer lb);

}
