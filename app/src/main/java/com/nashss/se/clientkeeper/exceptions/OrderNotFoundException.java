package com.nashss.se.clientkeeper.exceptions;

/**
 * General Exception for cases where an OrderId is invalid.
 */
public class OrderNotFoundException extends RuntimeException {

    /**
     * Exception with no message or cause.
     */
    public OrderNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public OrderNotFoundException(String message) {
        super(message);
    }
}
