package com.nashss.se.clientkeeper.dynamodb;
import com.nashss.se.clientkeeper.converters.DateConverter;
import com.nashss.se.clientkeeper.dynamodb.models.Client;
import com.nashss.se.clientkeeper.exceptions.ClientNotFoundException;
import com.nashss.se.clientkeeper.exceptions.InvalidAttributeValueException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Accesses data for a Client using {@link Client} to represent the model in DynamoDB.
 */
@Singleton
public class ClientDao {
    private final DateConverter dateConverter = new DateConverter();
    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates a ClientDao object.
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the Clients table
     */
    @Inject
    public ClientDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     * Returns the {@link Client} corresponding to the specified clientId.
     *
     * @param userEmail the User's email
     * @param clientId the Client's ID
     * @return the stored Client, or null if nothing was found.
     */
    public Client getClient(String userEmail, String clientId) {
        Client client = this.dynamoDbMapper.load(Client.class, userEmail, clientId);

        if (client == null) {
            throw new ClientNotFoundException("Client not found with clientId: " + clientId);
        }

        return client;
    }

    /**
     * Returns a list of {@link Client} corresponding to the specified userEmail.
     *
     * @param userEmail the User's email
     * @return a list of stored Clients for the given user email.
     */
    public List<Client> getAllClients(String userEmail) {
        DynamoDBQueryExpression<Client> queryExpression = new DynamoDBQueryExpression<Client>()
                .withKeyConditionExpression("userEmail = :userEmail")
                .withExpressionAttributeValues(Map.of(":userEmail", new AttributeValue().withS(userEmail)));

        return dynamoDbMapper.query(Client.class, queryExpression);
    }

    /**
     * Validates then saves (creates or updates) the given Client.
     *
     * @param client The client to save
     * @return The Client object that was saved
     */
    public Client saveClient(Client client) {
        validateClient(client);
        this.dynamoDbMapper.save(client);
        return client;
    }

    /**
     * Deletes the given Client.
     *
     * @param userEmail the User's email
     * @param clientId the Client's ID
     */
    public void deleteClient(String userEmail, String clientId) {
        Client client = getClient(userEmail, clientId);

        if (client != null) {
            this.dynamoDbMapper.delete(client);
        } else {
            throw new ClientNotFoundException("Client not found with clientId: " + clientId);
        }
    }

    /**
     * Validates the Client object has the proper values.
     *
     * @param client The client to validate.
     */
    private void validateClient(Client client) {
        if (client.getClientName() == null || client.getClientEmail() == null ||
                client.getClientPhone() == null || client.getClientAddress() == null ||
                client.getClientMemberSince() == null) {
            throw new InvalidAttributeValueException("Missing required attribute(s)");
        }

        try {
            dateConverter.convert(client.getClientMemberSince());
        } catch (Exception e) {
            throw new InvalidAttributeValueException("Invalid date format. Required format: yyyy-MM-dd");
        }
    }
}
