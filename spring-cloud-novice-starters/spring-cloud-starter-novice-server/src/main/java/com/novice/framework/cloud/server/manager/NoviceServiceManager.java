package com.novice.framework.cloud.server.manager;

import com.novice.framework.cloud.server.cache.CacheHelper;
import com.novice.framework.cloud.server.pojo.NoviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoviceServiceManager {
	private static final String CACHE_NAME = "simple_service";
	private final CacheManager cacheManager;
	private final CacheHelper cacheHelper;
	private Cache cache;

	@PostConstruct
	void init() {
		this.cache = this.cacheManager.getCache(CACHE_NAME);
	}

	public List<NoviceService> listAll() {
		return getServiceNames().stream().map(key -> get(key).orElse(null))
				.filter(Objects::nonNull).collect(Collectors.toList());
	}

	public Set<String> getServiceNames() {
		return this.cacheHelper.keys(this.cache);
	}

	public void save(NoviceService service) {
		this.cache.put(service.getName(), service);
	}

	public Optional<NoviceService> get(String serviceName) {
		Cache.ValueWrapper valueWrapper = this.cache.get(serviceName);
		if (valueWrapper != null) {
			return Optional.ofNullable((NoviceService) valueWrapper.get());
		}
		return Optional.empty();
	}

	public void delete(String serviceName) {
		this.cache.evict(serviceName);
	}
}