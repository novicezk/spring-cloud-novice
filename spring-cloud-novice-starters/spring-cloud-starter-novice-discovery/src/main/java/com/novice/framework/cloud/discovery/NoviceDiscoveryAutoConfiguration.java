/*
 * Copyright 2004-2020 Homolo Co., Ltd. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */
package com.novice.framework.cloud.discovery;


import com.novice.framework.cloud.discovery.client.NoviceDiscoveryClient;
import com.novice.framework.cloud.discovery.registry.NoviceAutoServiceRegistration;
import com.novice.framework.cloud.discovery.registry.NoviceRegistration;
import com.novice.framework.cloud.discovery.registry.NoviceServiceRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationAutoConfiguration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationConfiguration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnDiscoveryEnabled
@EnableConfigurationProperties(NoviceDiscoveryProperties.class)
@AutoConfigureAfter({AutoServiceRegistrationConfiguration.class,
		AutoServiceRegistrationAutoConfiguration.class})
public class NoviceDiscoveryAutoConfiguration {

	@Bean
	NoviceDiscoveryProperties noviceDiscoveryProperties() {
		return new NoviceDiscoveryProperties();
	}

	@Bean
	NoviceRegistration noviceRegistration() {
		return new NoviceRegistration(noviceDiscoveryProperties());
	}

	@Bean
	NoviceServiceRegistry noviceServiceRegistry() {
		return new NoviceServiceRegistry();
	}

	@Bean
	NoviceDiscoveryClient noviceDiscoveryClient() {
		return new NoviceDiscoveryClient(noviceDiscoveryProperties());
	}

	@Bean
	@ConditionalOnBean(AutoServiceRegistrationProperties.class)
	NoviceAutoServiceRegistration noviceAutoServiceRegistration(AutoServiceRegistrationProperties autoServiceRegistrationProperties) {
		return new NoviceAutoServiceRegistration(noviceServiceRegistry(), autoServiceRegistrationProperties, noviceRegistration());
	}

}
