package com.novice.framework.cloud.commons.result;

import lombok.Data;

@Data
public class Message<T> {
	private int code;
	private String description;
	private T result;

	public Message() {
	}

	public Message(MessageCode code) {
		this.code = code.getCode();
		this.description = code.getDescription();
	}

	public Message(MessageCode code, T result) {
		this.code = code.getCode();
		this.description = code.getDescription();
		this.result = result;
	}

	public Message(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public Message(int code, String description, T result) {
		this.code = code;
		this.description = description;
		this.result = result;
	}
}
