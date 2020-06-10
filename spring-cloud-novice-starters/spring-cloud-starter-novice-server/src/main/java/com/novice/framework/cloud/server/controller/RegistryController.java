/*
 * Copyright 2004-2020 Homolo Co., Ltd. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */
package com.novice.framework.cloud.server.controller;


import com.novice.framework.cloud.server.dto.InstanceRegisterDTO;
import com.novice.framework.cloud.server.manager.NoviceServiceManager;
import com.novice.framework.cloud.server.pojo.NoviceService;
import com.novice.framework.cloud.server.pojo.NoviceServiceInstance;
import com.novice.framework.cloud.server.result.Message;
import com.novice.framework.cloud.server.result.MessageCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/registry")
@RequiredArgsConstructor
public class RegistryController {
	private final NoviceServiceManager serviceManager;

	@PostMapping("/register")
	public Message<Void> register(@RequestBody InstanceRegisterDTO serviceInstanceDTO) {
		String serviceName = serviceInstanceDTO.getServiceId();
		Optional<NoviceService> optional = this.serviceManager.get(serviceName);
		NoviceService simpleService = optional.orElse(new NoviceService(serviceName));
		String instanceId = serviceInstanceDTO.getServiceId() + "@" + serviceInstanceDTO.getHost() + ":" + serviceInstanceDTO.getPort();
		boolean exists = simpleService.getInstances().stream().anyMatch(i -> instanceId.equals(i.getInstanceId()));
		if (exists) {
			return new Message<>(MessageCode.WARNING.getCode(), instanceId + " has registered");
		}
		NoviceServiceInstance instance = new NoviceServiceInstance();
		BeanUtils.copyProperties(serviceInstanceDTO, instance);
		instance.setInstanceId(instanceId);
		simpleService.getInstances().add(instance);
		this.serviceManager.save(simpleService);
		return new Message<>(MessageCode.SUCCESS);
	}

	@GetMapping("/services")
	public List<NoviceService> services() {
		return this.serviceManager.listAll();
	}
}
