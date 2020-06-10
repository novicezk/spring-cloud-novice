package com.novice.framework.cloud.server.cache;

import com.novice.framework.cloud.server.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.NoOpCache;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

@Component
public class CacheHelper implements CacheKeys {
	@Autowired
	private SimpleCacheKeys simpleCacheKeys;
	@Autowired(required = false)
	private RedisCacheKeys redisCacheKeys;

	@Value("${spring.cache.type}")
	private CacheType cacheType;

	@Override
	public Set<String> keys(Cache cache) {
		if (cache instanceof NoOpCache) {
			return Collections.emptySet();
		}
		if (cache instanceof ConcurrentMapCache) {
			return this.simpleCacheKeys.keys(cache);
		}
		if (CacheType.REDIS.equals(this.cacheType)) {
			if (this.redisCacheKeys == null) {
				throw new ValidationException("redis cache not implements");
			}
			return this.redisCacheKeys.keys(cache);
		}
		throw new ValidationException("not support cache type: " + this.cacheType);
	}
}
