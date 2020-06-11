/*
 * Copyright 2004-2020 Homolo Co., Ltd. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */
package com.novice.framework.cloud.server.controller;


import com.novice.framework.cloud.commons.client.NoviceServiceInstance;
import com.novice.framework.cloud.server.manager.NoviceServiceManager;
import com.novice.framework.cloud.server.pojo.NoviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/discovery")
@RequiredArgsConstructor
public class DiscoveryController {
	private final NoviceServiceManager serviceManager;

	@GetMapping("/services")
	public List<String> services() {
		return new ArrayList<>(this.serviceManager.getServiceNames());
	}

	@GetMapping("/{serviceName}/instances")
	public List<NoviceServiceInstance> instances(@PathVariable String serviceName) {
		return this.serviceManager.get(serviceName).map(NoviceService::getInstances)
				.orElse(Collections.emptyList());
	}
}
