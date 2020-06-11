package com.novice.framework.cloud.commons.result;

import lombok.Getter;

public enum MessageCode {
	/**
	 * SUCCESS.
	 */
	SUCCESS(1, "调用成功"),
	/**
	 * WARNING.
	 */
	WARNING(2, "警告信息"),
	/**
	 * NOT_FOUND.
	 */
	NOT_FOUND(-1, "数据未找到"),
	/**
	 * VALIDATION_ERROR.
	 */
	VALIDATION_ERROR(-2, "校验错误"),
	/**
	 * FAILURE.
	 */
	FAILURE(9, "系统异常");

	@Getter
	private int code;
	@Getter
	private String description;

	MessageCode(int code, String description) {
		this.code = code;
		this.description = description;
	}
}
