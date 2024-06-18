package com.nashss.se.clientkeeper;

import com.nashss.se.clientkeeper.exceptions.ClientNotFoundException;
import com.nashss.se.clientkeeper.exceptions.InvalidAttributeValueException;
import com.nashss.se.clientkeeper.exceptions.OrderNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExceptionsTest {

    @Test
    public void clientNotFoundException_withNoMessageOrCause() {
        // WHEN
        ClientNotFoundException exception = new ClientNotFoundException();

        // THEN
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void clientNotFoundException_withMessage() {
        // GIVEN
        String message = "Client not found";

        // WHEN
        ClientNotFoundException exception = new ClientNotFoundException(message);

        // THEN
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void invalidAttributeValueException_withNoMessageOrCause() {
        // WHEN
        InvalidAttributeValueException exception = new InvalidAttributeValueException();

        // THEN
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void invalidAttributeValueException_withMessage() {
        // GIVEN
        String message = "Invalid attribute value";

        // WHEN
        InvalidAttributeValueException exception = new InvalidAttributeValueException(message);

        // THEN
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void orderNotFoundException_withNoMessageOrCause() {
        // WHEN
        OrderNotFoundException exception = new OrderNotFoundException();

        // THEN
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void orderNotFoundException_withMessage() {
        // GIVEN
        String message = "Order not found";

        // WHEN
        OrderNotFoundException exception = new OrderNotFoundException(message);

        // THEN
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }
}
