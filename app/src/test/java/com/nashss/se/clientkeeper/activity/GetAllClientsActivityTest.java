package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.GetAllClientsRequest;
import com.nashss.se.clientkeeper.activity.results.GetAllClientsResult;
import com.nashss.se.clientkeeper.dynamodb.ClientDao;
import com.nashss.se.clientkeeper.dynamodb.models.Client;
import com.nashss.se.clientkeeper.exceptions.ClientNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetAllClientsActivityTest {
    @Mock
    private ClientDao clientDao;

    private GetAllClientsActivity getAllClientsActivity;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @BeforeEach
    public void setup() {
        initMocks(this);
        getAllClientsActivity = new GetAllClientsActivity(clientDao);
    }

    @Test
    public void handleRequest_withValidRequest_returnsClients() {
        // GIVEN
        GetAllClientsRequest request = GetAllClientsRequest.builder()
                .withUserEmail("user@example.com")
                .build();

        Client client = new Client();
        client.setUserEmail("user@example.com");
        client.setClientId("validClientId");
        client.setClientName("Jane Doe");
        client.setClientEmail("janedoe@example.com");
        client.setClientPhone("5786314899");
        client.setClientAddress("123 Main St Nashville, TN 37919");
        client.setClientMemberSince(LocalDate.parse("2024-01-27", DATE_FORMATTER));

        when(clientDao.getAllClients("user@example.com")).thenReturn(List.of(client));

        // WHEN
        GetAllClientsResult result = getAllClientsActivity.handleRequest(request);

        // THEN
        assertNotNull(result);
        assertNotNull(result.getClients());
        assertFalse(result.getClients().isEmpty());
        assertEquals("Jane Doe", result.getClients().get(0).getClientName());
        verify(clientDao).getAllClients("user@example.com");
    }

    @Test
    public void handleRequest_withNoClients_returnsEmptyList() {
        // GIVEN
        GetAllClientsRequest request = GetAllClientsRequest.builder()
                .withUserEmail("user@example.com")
                .build();

        when(clientDao.getAllClients("user@example.com")).thenReturn(Collections.emptyList());

        // WHEN
        GetAllClientsResult result = getAllClientsActivity.handleRequest(request);

        // THEN
        assertNotNull(result);
        assertNotNull(result.getClients());
        assertTrue(result.getClients().isEmpty());
        verify(clientDao).getAllClients("user@example.com");
    }

    @Test
    public void handleRequest_withNullClients_returnsEmptyList() {
        // GIVEN
        GetAllClientsRequest request = GetAllClientsRequest.builder()
                .withUserEmail("user@example.com")
                .build();

        when(clientDao.getAllClients("user@example.com")).thenReturn(null);

        // WHEN
        GetAllClientsResult result = getAllClientsActivity.handleRequest(request);

        // THEN
        assertNotNull(result);
        assertNotNull(result.getClients());
        assertTrue(result.getClients().isEmpty());
        verify(clientDao).getAllClients("user@example.com");
    }
}
