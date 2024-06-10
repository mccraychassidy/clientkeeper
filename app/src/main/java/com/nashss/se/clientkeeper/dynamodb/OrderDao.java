package com.nashss.se.clientkeeper.dynamodb;
import com.nashss.se.clientkeeper.dynamodb.models.Order;
import com.nashss.se.clientkeeper.exceptions.InvalidAttributeValueException;
import com.nashss.se.clientkeeper.exceptions.OrderNotFoundException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Accesses data for an Order using {@link Order} to represent the model in DynamoDB.
 */
@Singleton
public class OrderDao {
    private static final String DATE_FORMAT = "MM-dd-yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates an OrderDao object.
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the Orders table
     */
    @Inject
    public OrderDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     * Returns the {@link Order} corresponding to the specified orderId.
     *
     * @param userEmail the User's email
     * @param orderId the Order's ID
     * @return the stored Order, or null if nothing was found.
     */
    public Order getOrder(String userEmail, String orderId) {
        Order order = this.dynamoDbMapper.load(Order.class, userEmail, orderId);

        if (order == null) {
            throw new OrderNotFoundException("Order not found with orderId: " + orderId);
        }

        return order;
    }

    /**
     * Returns a list of {@link Order} corresponding to the specified userEmail.
     *
     * @param userEmail the User's email
     * @return a list of stored Orders for the given user email.
     */
    public List<Order> getAllOrders(String userEmail) {
        DynamoDBQueryExpression<Order> queryExpression = new DynamoDBQueryExpression<Order>()
                .withKeyConditionExpression("userEmail = :userEmail")
                .withExpressionAttributeValues(Map.of(":userEmail", new AttributeValue().withS(userEmail)));

        return dynamoDbMapper.query(Order.class, queryExpression);
    }

    /**
     * Returns a list of {@link Order} corresponding to the specified clientId using the GSI.
     *
     * @param clientId the Client's ID
     * @return a list of stored Orders for the given client ID.
     */
    public List<Order> getOrdersByClientId(String clientId) {
        DynamoDBQueryExpression<Order> queryExpression = new DynamoDBQueryExpression<Order>()
                .withIndexName("ClientOrdersIndex")
                .withConsistentRead(false)
                .withKeyConditionExpression("clientId = :clientId")
                .withExpressionAttributeValues(Map.of(":clientId", new AttributeValue().withS(clientId)));

        return dynamoDbMapper.query(Order.class, queryExpression);
    }

    /**
     * Validates then saves (creates or updates) the given Order.
     *
     * @param order The order to save
     * @return The Order object that was saved
     */
    public Order saveOrder(Order order) {
        validateOrder(order);
        this.dynamoDbMapper.save(order);
        return order;
    }

    /**
     * Deletes the given Order.
     *
     * @param userEmail the User's email
     * @param orderId the Order's ID
     */
    public void deleteOrder(String userEmail, String orderId) {
        Order order = getOrder(userEmail, orderId);

        if (order != null) {
            this.dynamoDbMapper.delete(order);
        } else {
            throw new OrderNotFoundException("Order not found with orderId: " + orderId);
        }
    }

    /**
     * Validates the Order object has the proper values.
     *
     * @param order The order to validate.
     */
    private void validateOrder(Order order) {
        if (order.getClientId() == null || order.getItem() == null ||
                order.getPurchaseDate() == null || order.getShippingService() == null ||
                order.getExpectedDate() == null || order.getDeliveredDate() == null) {
            throw new InvalidAttributeValueException("Missing required attribute(s)");
        }

        try {
            DATE_FORMATTER.format(order.getPurchaseDate());
            DATE_FORMATTER.format(order.getExpectedDate());
            DATE_FORMATTER.format(order.getDeliveredDate());
        } catch (Exception e) {
            throw new InvalidAttributeValueException("Invalid date format. Required format: " + DATE_FORMAT);
        }
    }
}
