package com.novice.framework.cloud.loadbalancer;


import com.novice.framework.cloud.loadbalancer.client.NoviceLoadBalancerClient;
import com.novice.framework.cloud.loadbalancer.rule.BestAvailableRule;
import com.novice.framework.cloud.loadbalancer.rule.IRule;
import com.novice.framework.cloud.loadbalancer.support.ServiceLoadBalancerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.AsyncLoadBalancerAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnDiscoveryEnabled
@EnableConfigurationProperties(NoviceLoadbalancerProperties.class)
@AutoConfigureBefore({AsyncLoadBalancerAutoConfiguration.class, LoadBalancerAutoConfiguration.class})
public class NoviceLoadbalancerAutoConfiguration {

	@Bean
	NoviceLoadbalancerProperties noviceLoadbalancerProperties() {
		return new NoviceLoadbalancerProperties();
	}

	@Bean
	@ConditionalOnMissingBean
	IRule defaultLoadbalancerRule() {
		return new BestAvailableRule();
	}

	@Bean
	ServiceLoadBalancerFactory serviceLoadBalancerFactory(DiscoveryClient discoveryClient) {
		return new ServiceLoadBalancerFactory(discoveryClient);
	}

	@Bean
	NoviceLoadBalancerClient noviceLoadBalancerClient(ServiceLoadBalancerFactory serviceLoadBalancerFactory, IRule rule) {
		return new NoviceLoadBalancerClient(serviceLoadBalancerFactory, rule);
	}
}
