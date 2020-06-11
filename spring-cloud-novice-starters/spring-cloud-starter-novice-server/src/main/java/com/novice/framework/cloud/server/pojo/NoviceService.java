/*
 * Copyright 2004-2020 Homolo Co., Ltd. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */
package com.novice.framework.cloud.server.pojo;

import com.novice.framework.cloud.commons.client.NoviceServiceInstance;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
}
