/*
 * Copyright 2004-2020 Homolo Co., Ltd. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */
package com.novice.framework.cloud.discovery;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


@Data
@ConfigurationProperties("spring.cloud.novice.discovery")
public class NoviceDiscoveryProperties {
	private String serverAddr;
	private String host;
	@Value("${spring.cloud.novice.discovery.port:${server.port:8080}}")
	private int port;
	@Value("${spring.cloud.novice.discovery.service:${spring.application.name:}}")
	private String service;
	private Map<String, String> metadata = new HashMap<>();
	private boolean secure = false;

	@Autowired
	private InetUtils inetUtils;

	@PostConstruct
	public void init() {
		if (StringUtils.isEmpty(this.host)) {
			this.host = this.inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
		}
		if (!StringUtils.startsWithIgnoreCase(this.serverAddr, "http")) {
			this.serverAddr = "http://" + this.serverAddr;
		}
	}
}
