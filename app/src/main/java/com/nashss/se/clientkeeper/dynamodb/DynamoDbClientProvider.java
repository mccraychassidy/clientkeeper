package com.nashss.se.clientkeeper.dynamodb;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;


/**
 * Provides AmazonDynamoDB client instances.
 */
public class DynamoDbClientProvider {
    /**
     * Returns an AmazonDynamoDB client for the specified region.
     *
     * @param region the AWS region to configure the client for
     * @return AmazonDynamoDB client
     */
    public static AmazonDynamoDB getDynamoDBClient(Regions region) {
        return AmazonDynamoDBClientBuilder.standard()
                .withRegion(region)
                .build();
    }
}
