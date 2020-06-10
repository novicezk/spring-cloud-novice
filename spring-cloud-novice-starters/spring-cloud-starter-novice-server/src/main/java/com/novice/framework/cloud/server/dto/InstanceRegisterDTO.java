/*
 * Copyright 2004-2020 Homolo Co., Ltd. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */
package com.novice.framework.cloud.server.dto;

import lombok.Data;

import java.util.Map;

@Data
public class InstanceRegisterDTO {
	private String serviceId;
	private String host;
	private int port;
	private boolean secure;
	private Map<String, String> metadata;
}