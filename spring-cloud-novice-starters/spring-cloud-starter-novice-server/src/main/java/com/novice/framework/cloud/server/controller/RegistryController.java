package com.novice.framework.cloud.server.controller;


import com.novice.framework.cloud.commons.client.NoviceServiceInstance;
import com.novice.framework.cloud.server.dto.RegisterInstanceDTO;
import com.novice.framework.cloud.server.manager.NoviceServiceManager;
import com.novice.framework.cloud.server.pojo.NoviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/registry")
@RequiredArgsConstructor
public class RegistryController {
	private final NoviceServiceManager serviceManager;

	@PutMapping("/register")
	public void register(@RequestBody RegisterInstanceDTO registerInstanceDTO) {
		String serviceName = registerInstanceDTO.getServiceId();
		Optional<NoviceService> optional = this.serviceManager.get(serviceName);
		NoviceService simpleService = optional.orElse(new NoviceService(serviceName));
		String instanceId = registerInstanceDTO.getInstanceId();
		boolean exists = simpleService.getInstances().stream().anyMatch(i -> instanceId.equals(i.getInstanceId()));
		if (!exists) {
			NoviceServiceInstance instance = new NoviceServiceInstance();
			instance.setStatus("UP");
			BeanUtils.copyProperties(registerInstanceDTO, instance);
			instance.setInstanceId(instanceId);
			simpleService.getInstances().add(instance);
			this.serviceManager.save(simpleService);
		}
	}

	@DeleteMapping("/deregister/{serviceName}/{instanceId}")
	public void deregister(@PathVariable String serviceName, @PathVariable String instanceId) {
		this.serviceManager.get(serviceName).ifPresent(service -> {
			boolean removed = service.removeInstance(instanceId);
			if (service.getInstances().isEmpty()) {
				this.serviceManager.delete(serviceName);
			} else if (removed) {
				this.serviceManager.save(service);
			}
		});
	}

	@PostMapping("/{serviceName}/{instanceId}/setStatus/{status}")
	public void setStatus(@PathVariable String serviceName, @PathVariable String instanceId, @PathVariable String status) {
		this.serviceManager.get(serviceName).ifPresent(service ->
				service.getInstance(instanceId).ifPresent(instance -> {
					instance.setStatus(status);
					this.serviceManager.save(service);
				}));
	}

	@GetMapping("/{serviceName}/{instanceId}/status")
	public String getStatus(@PathVariable String serviceName, @PathVariable String instanceId) {
		return this.serviceManager.get(serviceName)
				.flatMap(service -> service.getInstance(instanceId).map(NoviceServiceInstance::getStatus))
				.orElse(null);
	}

}
