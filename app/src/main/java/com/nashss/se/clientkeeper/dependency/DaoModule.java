package com.nashss.se.clientkeeper.dependency;
import com.nashss.se.clientkeeper.dynamodb.ClientDao;
import com.nashss.se.clientkeeper.dynamodb.DynamoDbClientProvider;
import com.nashss.se.clientkeeper.dynamodb.OrderDao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Dagger module that provides dependencies for DAO classes.
 */
@Module
public class DaoModule {
    /**
     * Provides a singleton instance of DynamoDBMapper.
     *
     * @return a DynamoDBMapper object
     */
    @Singleton
    @Provides
    public DynamoDBMapper provideDynamoDBMapper() {
        return new DynamoDBMapper(DynamoDbClientProvider.getDynamoDBClient(Regions.US_EAST_2));
    }

    /**
     * Provides a singleton instance of ClientDao.
     *
     * @param dynamoDbMapper used for data access
     * @return a new ClientDao instance
     */
    @Singleton
    @Provides
    public ClientDao provideClientDao(DynamoDBMapper dynamoDbMapper) {
        return new ClientDao(dynamoDbMapper);
    }

    /**
     * Provides a singleton instance of OrderDao.
     *
     * @param dynamoDbMapper used for data access
     * @return a new OrderDao instance
     */
    @Singleton
    @Provides
    public OrderDao provideOrderDao(DynamoDBMapper dynamoDbMapper) {
        return new OrderDao(dynamoDbMapper);
    }

    /**
     * Provides a singleton instance of DeleteClientActivity.
     *
     * @param clientDao used to interact with the database
     * @return a new DeleteClientActivity instance
     */
    @Singleton
    @Provides
    public DeleteClientActivity provideDeleteClientActivity(ClientDao clientDao) {
        return new DeleteClientActivity(clientDao);
    }
}

