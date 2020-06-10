package com.novice.framework.cloud.server.cache;

import com.novice.framework.cloud.server.exception.ValidationException;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Set;
import java.util.stream.Collectors;

public class RedisCacheKeys implements CacheKeys {
	private final StringRedisTemplate redisTemplate;

	public RedisCacheKeys(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public Set<String> keys(Cache cache) {
		if (!(cache instanceof RedisCache)) {
			throw new ValidationException("cache is not redis cache");
		}
		RedisCache redisCache = (RedisCache) cache;
		String keyPrefix = redisCache.getCacheConfiguration().getKeyPrefixFor(cache.getName());
		Set<String> redisKeys = this.redisTemplate.keys(keyPrefix + "*");
		return redisKeys.stream().map(key -> key.replace(keyPrefix, ""))
				.collect(Collectors.toSet());
	}
}
