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
import java.util.UUID;


@Data
@ConfigurationProperties("spring.cloud.novice.discovery")
public class NoviceDiscoveryProperties {
	private String serverAddr;
	@Value("${spring.cloud.novice.discovery.service:${spring.application.name:}}")
	private String service;
	private String instanceId;
	private String host;
	@Value("${spring.cloud.novice.discovery.port:${server.port:8080}}")
	private int port;
	private Map<String, String> metadata = new HashMap<>();
	private boolean secure = false;

	@Autowired
	private InetUtils inetUtils;

	@PostConstruct
	public void init() {
		if (StringUtils.isEmpty(this.host)) {
			this.host = this.inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
		}
		if (StringUtils.isEmpty(this.instanceId)) {
			this.instanceId = UUID.randomUUID().toString();
		}
		if (!StringUtils.startsWithIgnoreCase(this.serverAddr, "http")) {
			this.serverAddr = "http://" + this.serverAddr;
		}
	}
}
