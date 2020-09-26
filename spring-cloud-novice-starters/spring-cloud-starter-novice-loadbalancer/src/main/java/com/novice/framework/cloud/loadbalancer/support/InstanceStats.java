package com.novice.framework.cloud.loadbalancer.support;

import com.novice.framework.cloud.commons.util.LimitedQueue;
import lombok.Getter;
import org.springframework.cloud.client.ServiceInstance;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InstanceStats {
	private final ServiceInstance serviceInstance;
	private final AtomicInteger totalRequestsCount = new AtomicInteger(0);
	private final AtomicInteger activeRequestsCount = new AtomicInteger(0);
	private final AtomicInteger failureRequestsCount = new AtomicInteger(0);
	private volatile long lastRequestTime;
	private final List<InstanceRequest> latelyRequestQueue;

	public InstanceStats(ServiceInstance serviceInstance) {
		this.serviceInstance = serviceInstance;
		this.latelyRequestQueue = Collections.synchronizedList(new LimitedQueue<>(100));
	}

	public void incrementActiveRequestsCount() {
		this.activeRequestsCount.incrementAndGet();
		this.totalRequestsCount.incrementAndGet();
		this.lastRequestTime = System.currentTimeMillis();
	}

	public void addSuccessRequest(long startTime) {
		this.latelyRequestQueue.add(new InstanceRequest(startTime, System.currentTimeMillis() - startTime, true));
		decrementActiveRequestsCount();
	}

	public void addFailureRequest(long startTime) {
		this.latelyRequestQueue.add(new InstanceRequest(startTime, System.currentTimeMillis() - startTime, false));
		decrementActiveRequestsCount();
		this.failureRequestsCount.incrementAndGet();
	}

	private void decrementActiveRequestsCount() {
		if (this.activeRequestsCount.decrementAndGet() < 0) {
			this.activeRequestsCount.set(0);
		}
	}

	public int getActiveRequestsCount() {
		return this.activeRequestsCount.get();
	}

	public ServiceInstance getServiceInstance() {
		return this.serviceInstance;
	}

	public int getTotalRequestsCount() {
		return this.totalRequestsCount.get();
	}

	public int getFailureRequestsCount() {
		return this.failureRequestsCount.get();
	}

	public long getLastRequestTime() {
		return this.lastRequestTime;
	}

	public List<InstanceRequest> getLatelyRequestQueue() {
		return Collections.unmodifiableList(this.latelyRequestQueue);
	}

	@Getter
	public static class InstanceRequest {
		private final long startTime;
		private final long cost;
		private final boolean success;

		private InstanceRequest(long startTime, long cost, boolean success) {
			this.startTime = startTime;
			this.cost = cost;
			this.success = success;
		}
	}
}
