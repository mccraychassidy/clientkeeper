package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.CreateClientRequest;
import com.nashss.se.clientkeeper.activity.results.CreateClientResult;
import com.nashss.se.clientkeeper.dynamodb.ClientDao;
import com.nashss.se.clientkeeper.dynamodb.models.Client;
import com.nashss.se.clientkeeper.exceptions.InvalidAttributeValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreateClientActivityTest {
    @Mock
    private ClientDao clientDao;

    private CreateClientActivity createClientActivity;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @BeforeEach
    public void setup() {
        initMocks(this);
        createClientActivity = new CreateClientActivity(clientDao);
    }

    @Test
    public void handleRequest_withValidRequest_createsClient() {
        // GIVEN
        CreateClientRequest request = CreateClientRequest.builder()
                .withUserEmail("user@example.com")
                .withClientId("validClientId")
                .withClientName("Jane Doe")
                .withClientEmail("janedoe@example.com")
                .withClientPhone("5786314899")
                .withClientAddress("123 Main St Nashville, TN 37919")
                .withClientMemberSince("2024-01-27")
                .build();

        Client client = new Client();
        client.setUserEmail("user@example.com");
        client.setClientId("validClientId");
        client.setClientName("Jane Doe");
        client.setClientEmail("janedoe@example.com");
        client.setClientPhone("5786314899");
        client.setClientAddress("123 Main St Nashville, TN 37919");
        client.setClientMemberSince(LocalDate.parse("2024-01-27", DATE_FORMATTER));

        when(clientDao.saveClient(any(Client.class))).thenReturn(client);

        // WHEN
        CreateClientResult result = createClientActivity.handleRequest(request);

        // THEN
        assertNotNull(result);
        assertNotNull(result.getClient());
        assertEquals("Jane Doe", result.getClient().getClientName());
        verify(clientDao).saveClient(any(Client.class));
    }

    @Test
    public void handleRequest_withMissingAttributes_throwsInvalidAttributeValueException() {
        // GIVEN
        CreateClientRequest request = CreateClientRequest.builder()
                .withUserEmail("user@example.com")
                .withClientId("validClientId")
                .withClientName("Jane Doe")
                // Missing clientEmail
                .withClientPhone("5786314899")
                .withClientAddress("123 Main St Nashville, TN 37919")
                .withClientMemberSince("2024-01-27")
                .build();

        // WHEN + THEN
        assertThrows(InvalidAttributeValueException.class, () -> createClientActivity.handleRequest(request));
        verify(clientDao, never()).saveClient(any(Client.class));
    }
}
