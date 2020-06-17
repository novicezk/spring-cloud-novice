package com.novice.framework.cloud.loadbalancer.chooser;

public enum ChooserType {
	/**
	 * 随机.
	 */
	RANDOM,
	/**
	 * 轮询.
	 */
	ROUND_ROBIN;

	public static final ChooserType DEFAULT = ROUND_ROBIN;
}
