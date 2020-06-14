package com.novice.framework.cloud.server.pojo;

import com.novice.framework.cloud.commons.client.NoviceServiceInstance;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class NoviceService implements Serializable {
	private static final long serialVersionUID = -8361015282916126745L;

	private String name;
	private List<NoviceServiceInstance> instances = new ArrayList<>();

	public NoviceService() {
	}

	public NoviceService(String name) {
		this.name = name;
	}

	public Optional<NoviceServiceInstance> getInstance(String instanceId) {
		return this.instances.stream().filter(i -> i.getInstanceId().equals(instanceId)).findFirst();
	}

	public boolean removeInstance(String instanceId) {
		return this.instances.removeIf(i -> i.getInstanceId().equals(instanceId));
	}
}
