package com.novice.framework.cloud.configuration;


import com.novice.framework.cloud.server.cache.RedisCacheKeys;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;


@Configuration
@ConditionalOnClass(RedisTemplate.class)
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfiguration {

	@Bean
	RedisCacheKeys redisCacheKeys(StringRedisTemplate redisTemplate) {
		return new RedisCacheKeys(redisTemplate);
	}

}

