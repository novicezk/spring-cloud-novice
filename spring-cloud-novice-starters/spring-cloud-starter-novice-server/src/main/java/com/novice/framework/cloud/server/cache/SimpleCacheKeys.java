package com.novice.framework.cloud.server.cache;

import com.novice.framework.cloud.server.exception.ValidationException;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SimpleCacheKeys implements CacheKeys {

	@Override
	@SuppressWarnings("unchecked")
	public Set<String> keys(Cache cache) {
		if (!(cache instanceof ConcurrentMapCache)) {
			throw new ValidationException("cache is not simple cache");
		}
		Map<Object, Object> map = (Map<Object, Object>) cache.getNativeCache();
		return map.keySet().stream().map(key -> key instanceof String ? (String) key : String.valueOf(key))
				.collect(Collectors.toSet());
	}
}
