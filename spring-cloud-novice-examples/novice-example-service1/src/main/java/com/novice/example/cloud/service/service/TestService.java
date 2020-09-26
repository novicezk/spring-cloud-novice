/*
 * Copyright 2004-2020 Homolo Co., Ltd. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */
package com.novice.example.cloud.service.service;

import org.springframework.stereotype.Service;

@Service
public class TestService {

	public synchronized String test() {
		try {
			Thread.sleep(100L);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return "hello";
	}
}
