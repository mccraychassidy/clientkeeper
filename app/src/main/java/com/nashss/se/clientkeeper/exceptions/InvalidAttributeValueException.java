package com.nashss.se.clientkeeper.exceptions;

/**
 * General Exception for cases where an attribute is invalid.
 */
public class InvalidAttributeValueException extends RuntimeException {

    /**
     * Exception with no message or cause.
     */
    public InvalidAttributeValueException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public InvalidAttributeValueException(String message) {
        super(message);
    }

}
