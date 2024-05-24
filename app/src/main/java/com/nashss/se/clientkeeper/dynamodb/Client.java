package com.nashss.se.clientkeeper.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Represents a Client in the Clients table
 */
@DynamoDBTable(tableName = "clients")
public class Client {
    private String userEmail;
    private String clientId;
    private String clientName;
    private String clientEmail;
    private String clientPhone;
    private String clientAddress;
    private String clientMemberSince;

    @DynamoDBHashKey(attributeName = "userEmail")
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @DynamoDBRangeKey(attributeName = "clientId")
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @DynamoDBAttribute(attributeName = "clientName")
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @DynamoDBAttribute(attributeName = "clientEmail")
    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    @DynamoDBAttribute(attributeName = "clientPhone")
    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    @DynamoDBAttribute(attributeName = "clientAddress")
    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    @DynamoDBAttribute(attributeName = "clientMemberSince")
    public String getClientMemberSince() {
        return clientMemberSince;
    }

    public void setClientMemberSince(String clientMemberSince) {
        this.clientMemberSince = clientMemberSince;
    }
}
