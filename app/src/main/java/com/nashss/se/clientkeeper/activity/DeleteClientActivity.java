package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.DeleteClientRequest;
import com.nashss.se.clientkeeper.activity.results.DeleteClientResult;
import com.nashss.se.clientkeeper.dynamodb.ClientDao;
import com.nashss.se.clientkeeper.exceptions.ClientNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Handles the deletion of a client.
 */
public class DeleteClientActivity {
    private final Logger log = LogManager.getLogger();
    private final ClientDao clientDao;

    /**
     * Constructs a DeleteClientActivity with the given ClientDao.
     *
     * @param clientDao interacts with the database
     */
    @Inject
    public DeleteClientActivity(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    /**
     * Handles the request to delete a client.
     *
     * @param deleteClientRequest the request containing client information
     * @return the result of the client deletion
     * @throws ClientNotFoundException if the client is not found
     */
    public DeleteClientResult handleRequest(final DeleteClientRequest deleteClientRequest) {
        log.info("Received DeleteClientRequest {}", deleteClientRequest);

        clientDao.deleteClient(deleteClientRequest.getUserEmail(), deleteClientRequest.getClientId());

        return DeleteClientResult.builder()
                .withSuccess(true)
                .build();
    }
}
