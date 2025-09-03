package com.system.booking.exception;

public class PasswordNotMatchException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public PasswordNotMatchException() {
        super("Resource not found!");
    }

    public PasswordNotMatchException(String message) {
        super(message);
    }

    public PasswordNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
