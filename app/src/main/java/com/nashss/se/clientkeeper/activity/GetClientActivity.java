package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.GetClientRequest;
import com.nashss.se.clientkeeper.activity.results.GetClientResult;
import com.nashss.se.clientkeeper.converters.ModelConverter;
import com.nashss.se.clientkeeper.dynamodb.ClientDao;
import com.nashss.se.clientkeeper.dynamodb.models.Client;
import com.nashss.se.clientkeeper.exceptions.ClientNotFoundException;
import com.nashss.se.clientkeeper.models.ClientModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Handles the retrieval of a client.
 */
public class GetClientActivity {
    private final Logger log = LogManager.getLogger();
    private final ClientDao clientDao;

    /**
     * Constructs a GetClientActivity with the given ClientDao.
     *
     * @param clientDao interacts with the database
     */
    @Inject
    public GetClientActivity(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    /**
     * Handles the request to get a client.
     *
     * @param getClientRequest the request containing client ID and user email
     * @return the result of the client retrieval
     * @throws ClientNotFoundException if the client is not found
     */
    public GetClientResult handleRequest(final GetClientRequest getClientRequest) {
        log.info("Received GetClientRequest {}", getClientRequest);

        Client client = clientDao.getClient(getClientRequest.getUserEmail(), getClientRequest.getClientId());
        if (client == null) {
            throw new ClientNotFoundException("Client not found with clientId: " + getClientRequest.getClientId());
        }

        ClientModel clientModel = new ModelConverter().toClientModel(client);

        return GetClientResult.builder()
                .withClient(clientModel)
                .build();
    }
}
