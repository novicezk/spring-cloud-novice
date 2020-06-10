package com.novice.framework.cloud.server.cache;

import org.springframework.cache.Cache;

import java.util.Set;

public interface CacheKeys {

	Set<String> keys(Cache cache);
}
