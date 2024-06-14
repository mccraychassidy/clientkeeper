package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.EditClientRequest;
import com.nashss.se.clientkeeper.activity.results.EditClientResult;
import com.nashss.se.clientkeeper.converters.ModelConverter;
import com.nashss.se.clientkeeper.dynamodb.ClientDao;
import com.nashss.se.clientkeeper.dynamodb.models.Client;
import com.nashss.se.clientkeeper.exceptions.ClientNotFoundException;
import com.nashss.se.clientkeeper.models.ClientModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import javax.inject.Inject;

/**
 * Handles the editing of a client.
 */
public class EditClientActivity {
    private final Logger log = LogManager.getLogger();
    private final ClientDao clientDao;

    /**
     * Constructs an EditClientActivity with the given ClientDao.
     *
     * @param clientDao interacts with the database
     */
    @Inject
    public EditClientActivity(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    /**
     * Handles the request to edit a client.
     *
     * @param request the request containing the client information to be edited
     * @return the result of the client edit
     * @throws ClientNotFoundException if the client is not found
     */
    public EditClientResult handleRequest(final EditClientRequest request) {
        log.info("Received EditClientRequest for clientId: {}", request.getClientId());

        Client existingClient = clientDao.getClient(request.getUserEmail(), request.getClientId());

        if (existingClient == null) {
            throw new ClientNotFoundException("Client not found with clientId: " + request.getClientId());
        }

        existingClient.setClientName(request.getClientName());
        existingClient.setClientEmail(request.getClientEmail());
        existingClient.setClientPhone(request.getClientPhone());
        existingClient.setClientAddress(request.getClientAddress());
        existingClient.setClientMemberSince(LocalDate.parse(request.getClientMemberSince()));

        clientDao.saveClient(existingClient);

        ClientModel updatedClientModel = new ModelConverter().toClientModel(existingClient);

        return EditClientResult.builder()
                .withClient(updatedClientModel)
                .build();
    }
}
