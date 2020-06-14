package com.novice.framework.cloud.server.dto;

import lombok.Data;

import java.util.Map;

@Data
public class RegisterInstanceDTO {
	private String serviceId;
	private String instanceId;
	private String host;
	private int port;
	private boolean secure;
	private Map<String, String> metadata;
}