package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.GetClientRequest;
import com.nashss.se.clientkeeper.activity.results.GetClientResult;
import com.nashss.se.clientkeeper.dynamodb.ClientDao;
import com.nashss.se.clientkeeper.dynamodb.models.Client;
import com.nashss.se.clientkeeper.exceptions.ClientNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetClientActivityTest {
    @Mock
    private ClientDao clientDao;

    private GetClientActivity getClientActivity;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @BeforeEach
    public void setup() {
        initMocks(this);
        getClientActivity = new GetClientActivity(clientDao);
    }

    @Test
    public void handleRequest_withValidRequest_returnsClient() {
        // GIVEN
        GetClientRequest request = GetClientRequest.builder()
                .withUserEmail("user@example.com")
                .withClientId("validClientId")
                .build();

        Client client = new Client();
        client.setUserEmail("user@example.com");
        client.setClientId("validClientId");
        client.setClientName("Jane Doe");
        client.setClientEmail("janedoe@example.com");
        client.setClientPhone("5786314899");
        client.setClientAddress("123 Main St Nashville, TN 37919");
        client.setClientMemberSince(LocalDate.parse("2024-01-27", DATE_FORMATTER));

        when(clientDao.getClient("user@example.com", "validClientId")).thenReturn(client);

        // WHEN
        GetClientResult result = getClientActivity.handleRequest(request);

        // THEN
        assertNotNull(result);
        assertNotNull(result.getClient());
        assertEquals("Jane Doe", result.getClient().getClientName());
        verify(clientDao).getClient("user@example.com", "validClientId");
    }

    @Test
    public void handleRequest_withInvalidClientId_throwsClientNotFoundException() {
        // GIVEN
        GetClientRequest request = GetClientRequest.builder()
                .withUserEmail("user@example.com")
                .withClientId("invalidClientId")
                .build();

        when(clientDao.getClient("user@example.com", "invalidClientId")).thenThrow(new ClientNotFoundException("Client not found with clientId: invalidClientId"));

        // WHEN + THEN
        assertThrows(ClientNotFoundException.class, () -> getClientActivity.handleRequest(request));
        verify(clientDao).getClient("user@example.com", "invalidClientId");
    }
}
