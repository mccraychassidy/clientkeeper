package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.EditClientRequest;
import com.nashss.se.clientkeeper.activity.results.EditClientResult;
import com.nashss.se.clientkeeper.dynamodb.ClientDao;
import com.nashss.se.clientkeeper.dynamodb.models.Client;
import com.nashss.se.clientkeeper.exceptions.ClientNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class EditClientActivityTest {
    @Mock
    private ClientDao clientDao;

    private EditClientActivity editClientActivity;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @BeforeEach
    public void setup() {
        initMocks(this);
        editClientActivity = new EditClientActivity(clientDao);
    }

    @Test
    public void handleRequest_withValidRequest_editsClient() {
        // GIVEN
        EditClientRequest request = EditClientRequest.builder()
                .withUserEmail("user@example.com")
                .withClientId("validClientId")
                .withClientName("Jane Doe Updated")
                .withClientEmail("janedoe_updated@example.com")
                .withClientPhone("5786314899")
                .withClientAddress("123 Main St Nashville, TN 37919")
                .withClientMemberSince("2024-01-27")
                .build();

        Client existingClient = new Client();
        existingClient.setUserEmail("user@example.com");
        existingClient.setClientId("validClientId");
        existingClient.setClientName("Jane Doe");
        existingClient.setClientEmail("janedoe@example.com");
        existingClient.setClientPhone("5786314899");
        existingClient.setClientAddress("123 Main St Nashville, TN 37919");
        existingClient.setClientMemberSince(LocalDate.parse("2024-01-27", DATE_FORMATTER));

        when(clientDao.getClient("user@example.com", "validClientId")).thenReturn(existingClient);

        // WHEN
        EditClientResult result = editClientActivity.handleRequest(request);

        // THEN
        assertNotNull(result);
        assertNotNull(result.getClient());
        assertEquals("Jane Doe Updated", result.getClient().getClientName());
        assertEquals("janedoe_updated@example.com", result.getClient().getClientEmail());
        verify(clientDao).getClient("user@example.com", "validClientId");
        verify(clientDao).saveClient(any(Client.class));
    }

    @Test
    public void handleRequest_withInvalidClientId_throwsClientNotFoundException() {
        // GIVEN
        EditClientRequest request = EditClientRequest.builder()
                .withUserEmail("user@example.com")
                .withClientId("invalidClientId")
                .withClientName("Jane Doe Updated")
                .withClientEmail("janedoe_updated@example.com")
                .withClientPhone("5786314899")
                .withClientAddress("123 Main St Nashville, TN 37919")
                .withClientMemberSince("2024-01-27")
                .build();

        when(clientDao.getClient("user@example.com", "invalidClientId"))
                .thenThrow(new ClientNotFoundException("Client not found with clientId: invalidClientId"));

        // WHEN + THEN
        assertThrows(ClientNotFoundException.class, () -> editClientActivity.handleRequest(request));
        verify(clientDao).getClient("user@example.com", "invalidClientId");
        verify(clientDao, never()).saveClient(any(Client.class));
    }
}
