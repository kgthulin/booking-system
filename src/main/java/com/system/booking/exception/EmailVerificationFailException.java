package com.system.booking.exception;

public class EmailVerificationFailException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public EmailVerificationFailException() {
        super("Email verification failed!");
    }

    public EmailVerificationFailException(String message) {
        super(message);
    }

    public EmailVerificationFailException(String message, Throwable cause) {
        super(message, cause);
    }
}
