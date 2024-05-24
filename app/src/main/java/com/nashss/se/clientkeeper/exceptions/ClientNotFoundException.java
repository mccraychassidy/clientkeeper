package com.nashss.se.clientkeeper.exceptions;

/**
 * General Exception for cases where a ClientId is invalid.
 */
public class ClientNotFoundException extends RuntimeException {

    /**
     * Exception with no message or cause.
     */
    public ClientNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public ClientNotFoundException(String message) {
        super(message);
    }

}
