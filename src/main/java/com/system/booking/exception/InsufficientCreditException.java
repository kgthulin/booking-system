package com.system.booking.exception;

public class InsufficientCreditException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public InsufficientCreditException() {
        super("Insufficient credit!");
    }

    public InsufficientCreditException(String message) {
        super(message);
    }

    public InsufficientCreditException(String message, Throwable cause) {
        super(message, cause);
    }
}
