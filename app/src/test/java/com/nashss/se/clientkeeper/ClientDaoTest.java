package com.nashss.se.clientkeeper;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.clientkeeper.dynamodb.Client;
import com.nashss.se.clientkeeper.dynamodb.ClientDao;
import com.nashss.se.clientkeeper.exceptions.ClientNotFoundException;
import com.nashss.se.clientkeeper.exceptions.InvalidAttributeValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClientDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;

    private ClientDao clientDao;

    @BeforeEach
    public void setup() {
        initMocks(this);
        clientDao = new ClientDao(dynamoDBMapper);
    }

    @Test
    public void getClient_withValidClientId_returnsClient() {
        // GIVEN
        String userEmail = "user@example.com";
        String clientId = "validClientId";
        Client expectedClient = new Client();
        expectedClient.setUserEmail(userEmail);
        expectedClient.setClientId(clientId);

        when(dynamoDBMapper.load(Client.class, userEmail, clientId)).thenReturn(expectedClient);

        // WHEN
        Client client = clientDao.getClient(userEmail, clientId);

        // THEN
        assertNotNull(client);
        assertEquals(expectedClient, client);
        verify(dynamoDBMapper).load(Client.class, userEmail, clientId);
    }

    @Test
    public void getClient_withInvalidClientId_throwsClientNotFoundException() {
        // GIVEN
        String userEmail = "user@example.com";
        String clientId = "invalidClientId";

        when(dynamoDBMapper.load(Client.class, userEmail, clientId)).thenReturn(null);

        // WHEN + THEN
        assertThrows(ClientNotFoundException.class, () -> clientDao.getClient(userEmail, clientId));
        verify(dynamoDBMapper).load(Client.class, userEmail, clientId);
    }

    @Test
    public void saveClient_withValidClient_savesClient() {
        // GIVEN
        Client client = new Client();
        client.setClientName("Jane Doe");
        client.setClientEmail("janedoe@example.com");
        client.setClientPhone("5786314899");
        client.setClientAddress("123 Main St Nashville, TN 37919");
        client.setClientMemberSince("01/01/2024");
        client.setUserEmail("user@example.com");
        client.setClientId("validClientId");

        // WHEN
        Client result = clientDao.saveClient(client);

        // THEN
        verify(dynamoDBMapper).save(client);
        assertEquals(client, result);
    }

    @Test
    public void saveClient_withMissingAttributes_throwsInvalidAttributeValueException() {
        // GIVEN
        Client client = new Client();
        client.setClientEmail("jane.doe@example.com");

        // WHEN + THEN
        assertThrows(InvalidAttributeValueException.class, () -> clientDao.saveClient(client));
    }

    @Test
    public void deleteClient_withValidClientId_deletesClient() {
        // GIVEN
        String userEmail = "user@example.com";
        String clientId = "validClientId";
        Client client = new Client();
        client.setUserEmail(userEmail);
        client.setClientId(clientId);

        when(dynamoDBMapper.load(Client.class, userEmail, clientId)).thenReturn(client);

        // WHEN
        clientDao.deleteClient(userEmail, clientId);

        // THEN
        verify(dynamoDBMapper).delete(client);
    }

    @Test
    public void deleteClient_withInvalidClientId_throwsClientNotFoundException() {
        // GIVEN
        String userEmail = "user@example.com";
        String clientId = "invalidClientId";

        when(dynamoDBMapper.load(Client.class, userEmail, clientId)).thenReturn(null);

        // WHEN + THEN
        assertThrows(ClientNotFoundException.class, () -> clientDao.deleteClient(userEmail, clientId));
    }
}