package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.CreateClientRequest;
import com.nashss.se.clientkeeper.activity.results.CreateClientResult;
import com.nashss.se.clientkeeper.dynamodb.ClientDao;
import com.nashss.se.clientkeeper.dynamodb.models.Client;
import com.nashss.se.clientkeeper.exceptions.InvalidAttributeValueException;
import com.nashss.se.clientkeeper.models.ClientModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Handles the creation of a new client.
 */
public class CreateClientActivity {
    private final Logger log = LogManager.getLogger();
    private final ClientDao clientDao;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    /**
     * Constructs a CreateClientActivity with the given ClientDao.
     *
     * @param clientDao interacts with the database
     */
    @Inject
    public CreateClientActivity(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    /**
     * Handles the request to create a new client.
     *
     * @param createClientRequest the request containing client information
     * @return the result of the client creation
     * @throws InvalidAttributeValueException if any required attribute is missing
     */
    public CreateClientResult handleRequest(final CreateClientRequest createClientRequest) {
        log.info("Received CreateClientRequest {}", createClientRequest);

        if (createClientRequest.getClientName() == null || createClientRequest.getClientEmail() == null ||
                createClientRequest.getClientPhone() == null || createClientRequest.getClientAddress() == null ||
                createClientRequest.getClientMemberSince() == null) {
            throw new InvalidAttributeValueException("Missing required attribute(s)");
        }

        Client newClient = new Client();
        newClient.setUserEmail(createClientRequest.getUserEmail());
        newClient.setClientId(createClientRequest.getClientId());
        newClient.setClientName(createClientRequest.getClientName());
        newClient.setClientEmail(createClientRequest.getClientEmail());
        newClient.setClientPhone(createClientRequest.getClientPhone());
        newClient.setClientAddress(createClientRequest.getClientAddress());
        newClient.setClientMemberSince(LocalDate.parse(createClientRequest.getClientMemberSince(), DATE_FORMATTER));
        clientDao.saveClient(newClient);

        ClientModel clientModel = ClientModel.builder()
                .withUserEmail(newClient.getUserEmail())
                .withClientId(newClient.getClientId())
                .withClientName(newClient.getClientName())
                .withClientEmail(newClient.getClientEmail())
                .withClientPhone(newClient.getClientPhone())
                .withClientAddress(newClient.getClientAddress())
                .withClientMemberSince(newClient.getClientMemberSince().format(DATE_FORMATTER))
                .build();

        return CreateClientResult.builder()
                .withClient(clientModel)
                .build();
    }
}
