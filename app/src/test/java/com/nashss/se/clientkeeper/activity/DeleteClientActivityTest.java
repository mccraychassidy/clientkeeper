package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.DeleteClientRequest;
import com.nashss.se.clientkeeper.activity.results.DeleteClientResult;
import com.nashss.se.clientkeeper.dynamodb.ClientDao;
import com.nashss.se.clientkeeper.exceptions.ClientNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class DeleteClientActivityTest {
    @Mock
    private ClientDao clientDao;

    private DeleteClientActivity deleteClientActivity;

    @BeforeEach
    public void setup() {
        initMocks(this);
        deleteClientActivity = new DeleteClientActivity(clientDao);
    }

    @Test
    public void handleRequest_withValidRequest_deletesClient() {
        // GIVEN
        DeleteClientRequest request = DeleteClientRequest.builder()
                .withUserEmail("user@example.com")
                .withClientId("validClientId")
                .build();

        // WHEN
        DeleteClientResult result = deleteClientActivity.handleRequest(request);

        // THEN
        assertNotNull(result);
        assertTrue(result.isSuccess());
        verify(clientDao).deleteClient("user@example.com", "validClientId");
    }

    @Test
    public void handleRequest_withInvalidClientId_throwsClientNotFoundException() {
        // GIVEN
        DeleteClientRequest request = DeleteClientRequest.builder()
                .withUserEmail("user@example.com")
                .withClientId("invalidClientId")
                .build();

        doThrow(new ClientNotFoundException("Client not found with clientId: invalidClientId"))
                .when(clientDao).deleteClient("user@example.com", "invalidClientId");

        // WHEN + THEN
        assertThrows(ClientNotFoundException.class, () -> deleteClientActivity.handleRequest(request));
        verify(clientDao).deleteClient("user@example.com", "invalidClientId");
    }
}
