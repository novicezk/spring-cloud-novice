package com.novice.framework.cloud.server.exception;


public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = -8737811566272581355L;

	public ValidationException() {
		super();
	}

	public ValidationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ValidationException(String msg) {
		super(msg);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}

}
