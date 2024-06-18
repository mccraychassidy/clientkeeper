package com.nashss.se.clientkeeper.dynamodb;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DynamoDbClientProviderTest {

    @Test
    public void getDynamoDBClient_withValidRegion_returnsClient() {
        // GIVEN
        Regions region = Regions.US_EAST_1;

        // WHEN
        AmazonDynamoDB client = DynamoDbClientProvider.getDynamoDBClient(region);

        // THEN
        assertNotNull(client);
    }

    @Test
    public void getDynamoDBClient_withNullRegion_throwsException() {
        // GIVEN
        Regions region = null;

        // WHEN + THEN
        assertThrows(NullPointerException.class, () -> DynamoDbClientProvider.getDynamoDBClient(region));
    }
}
