package com.nashss.se.clientkeeper.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientModelTest {

    @Test
    public void testEquals_sameValues_shouldReturnTrue() {
        ClientModel client1 = ClientModel.builder()
                .withUserEmail("user@example.com")
                .withClientId("client123")
                .withClientName("John Doe")
                .withClientEmail("john@example.com")
                .withClientPhone("1234567890")
                .withClientAddress("123 Main St")
                .withClientMemberSince("2021-01-01")
                .build();

        ClientModel client2 = ClientModel.builder()
                .withUserEmail("user@example.com")
                .withClientId("client123")
                .withClientName("John Doe")
                .withClientEmail("john@example.com")
                .withClientPhone("1234567890")
                .withClientAddress("123 Main St")
                .withClientMemberSince("2021-01-01")
                .build();

        assertTrue(client1.equals(client2) && client2.equals(client1));
        assertEquals(client1.hashCode(), client2.hashCode());
    }

    @Test
    public void testEquals_differentValues_shouldReturnFalse() {
        ClientModel client1 = ClientModel.builder()
                .withUserEmail("user@example.com")
                .withClientId("client123")
                .build();

        ClientModel client2 = ClientModel.builder()
                .withUserEmail("user@example.com")
                .withClientId("client456")
                .build();

        assertFalse(client1.equals(client2));
        assertNotEquals(client1.hashCode(), client2.hashCode());
    }

    @Test
    public void testBuilder_correctInitialization() {
        String userEmail = "user@example.com";
        String clientId = "client123";
        String clientName = "John Doe";
        String clientEmail = "john@example.com";
        String clientPhone = "1234567890";
        String clientAddress = "123 Main St";
        String clientMemberSince = "2021-01-01";

        ClientModel client = ClientModel.builder()
                .withUserEmail(userEmail)
                .withClientId(clientId)
                .withClientName(clientName)
                .withClientEmail(clientEmail)
                .withClientPhone(clientPhone)
                .withClientAddress(clientAddress)
                .withClientMemberSince(clientMemberSince)
                .build();

        assertAll("Client should be set up correctly",
                () -> assertEquals(userEmail, client.getUserEmail()),
                () -> assertEquals(clientId, client.getClientId()),
                () -> assertEquals(clientName, client.getClientName()),
                () -> assertEquals(clientEmail, client.getClientEmail()),
                () -> assertEquals(clientPhone, client.getClientPhone()),
                () -> assertEquals(clientAddress, client.getClientAddress()),
                () -> assertEquals(clientMemberSince, client.getClientMemberSince())
        );
    }
}
