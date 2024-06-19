package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.GetAllClientsRequest;
import com.nashss.se.clientkeeper.activity.results.GetAllClientsResult;
import com.nashss.se.clientkeeper.dynamodb.ClientDao;

import com.nashss.se.clientkeeper.dynamodb.models.Client;
import com.nashss.se.clientkeeper.exceptions.ClientNotFoundException;
import com.nashss.se.clientkeeper.models.ClientModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;

/**
 * Handles the retrieval of all clients.
 */
public class GetAllClientsActivity {
    private final Logger log = LogManager.getLogger();
    private final ClientDao clientDao;

    /**
     * Constructs a GetAllClientsActivity with the given ClientDao.
     *
     * @param clientDao interacts with the database
     */
    @Inject
    public GetAllClientsActivity(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    /**
     * Handles the request to get all clients for a user.
     *
     * @param request the request containing the user email
     * @return the result of the clients retrieval
     * @throws ClientNotFoundException if the clients are not found for the user email
     */
    public GetAllClientsResult handleRequest(final GetAllClientsRequest request) {
        log.info("Received GetAllClientsRequest for userEmail: {}", request.getUserEmail());

        List<Client> clients = clientDao.getAllClients(request.getUserEmail());

        if (clients == null) {
            clients = Collections.emptyList();
        }

        List<ClientModel> clientModels = clients.stream().map(client ->
                ClientModel.builder()
                        .withUserEmail(client.getUserEmail())
                        .withClientId(client.getClientId())
                        .withClientName(client.getClientName())
                        .withClientEmail(client.getClientEmail())
                        .withClientPhone(client.getClientPhone())
                        .withClientAddress(client.getClientAddress())
                        .withClientMemberSince(client.getClientMemberSince().toString())
                        .build()
        ).collect(Collectors.toList());

        return GetAllClientsResult.builder()
                .withClients(clientModels)
                .build();
    }
}
