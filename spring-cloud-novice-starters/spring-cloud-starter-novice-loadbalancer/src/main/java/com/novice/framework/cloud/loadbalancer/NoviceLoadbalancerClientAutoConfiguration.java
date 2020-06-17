package com.novice.framework.cloud.loadbalancer;


import com.novice.framework.cloud.loadbalancer.chooser.Chooser;
import com.novice.framework.cloud.loadbalancer.chooser.RandomChooser;
import com.novice.framework.cloud.loadbalancer.chooser.RoundRobinChooser;
import com.novice.framework.cloud.loadbalancer.client.NoviceLoadBalancerClient;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.AsyncLoadBalancerAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnDiscoveryEnabled
@EnableConfigurationProperties(NoviceLoadbalancerProperties.class)
@AutoConfigureBefore({AsyncLoadBalancerAutoConfiguration.class, LoadBalancerAutoConfiguration.class})
public class NoviceLoadbalancerClientAutoConfiguration {

	@Bean
	NoviceLoadbalancerProperties noviceLoadbalancerProperties() {
		return new NoviceLoadbalancerProperties();
	}

	@Bean
	NoviceLoadBalancerClient noviceLoadBalancerClient(DiscoveryClient discoveryClient, Chooser chooser) {
		return new NoviceLoadBalancerClient(discoveryClient, chooser);
	}

	@Bean
	@Conditional(ChooserCondition.class)
	Chooser randomChooser() {
		return new RandomChooser();
	}

	@Bean
	@Conditional(ChooserCondition.class)
	Chooser roundRobinChooser() {
		return new RoundRobinChooser();
	}
}
