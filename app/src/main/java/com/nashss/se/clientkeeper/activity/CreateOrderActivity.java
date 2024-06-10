package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.CreateOrderRequest;
import com.nashss.se.clientkeeper.activity.results.CreateOrderResult;
import com.nashss.se.clientkeeper.dynamodb.OrderDao;
import com.nashss.se.clientkeeper.dynamodb.models.Order;
import com.nashss.se.clientkeeper.exceptions.InvalidAttributeValueException;
import com.nashss.se.clientkeeper.models.OrderModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Handles the creation of a new order.
 */
public class CreateOrderActivity {
    private final Logger log = LogManager.getLogger();
    private final OrderDao orderDao;

    /**
     * Constructs a CreateOrderActivity with the given OrderDao.
     *
     * @param orderDao interacts with the database
     */
    @Inject
    public CreateOrderActivity(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    /**
     * Handles the request to create a new order.
     *
     * @param createOrderRequest the request containing order information
     * @return the result of the order creation
     * @throws InvalidAttributeValueException if any required attribute is missing
     */
    public CreateOrderResult handleRequest(final CreateOrderRequest createOrderRequest) {
        log.info("Received CreateOrderRequest {}", createOrderRequest);

        if (createOrderRequest.getItem() == null || createOrderRequest.getPurchaseDate() == null ||
                createOrderRequest.getShippingService() == null || createOrderRequest.getExpectedDate() == null ||
                createOrderRequest.getDeliveredDate() == null) {
            throw new InvalidAttributeValueException("Missing required attribute(s)");
        }

        Order newOrder = new Order();
        newOrder.setUserEmail(createOrderRequest.getUserEmail());
        newOrder.setOrderId(createOrderRequest.getOrderId());
        newOrder.setClientId(createOrderRequest.getClientId());
        newOrder.setItem(createOrderRequest.getItem());
        newOrder.setShipped(createOrderRequest.getShipped());
        newOrder.setPurchaseDate(createOrderRequest.getPurchaseDate());
        newOrder.setShippingService(createOrderRequest.getShippingService());
        newOrder.setExpectedDate(createOrderRequest.getExpectedDate());
        newOrder.setDeliveredDate(createOrderRequest.getDeliveredDate());
        newOrder.setTrackingNumber(createOrderRequest.getTrackingNumber());
        newOrder.setReference(createOrderRequest.getReference());
        orderDao.saveOrder(newOrder);

        OrderModel orderModel = OrderModel.builder()
                .withUserEmail(newOrder.getUserEmail())
                .withOrderId(newOrder.getOrderId())
                .withClientId(newOrder.getClientId())
                .withItem(newOrder.getItem())
                .withShipped(newOrder.getShipped())
                .withPurchaseDate(newOrder.getPurchaseDate())
                .withShippingService(newOrder.getShippingService())
                .withExpectedDate(newOrder.getExpectedDate())
                .withDeliveredDate(newOrder.getDeliveredDate())
                .withTrackingNumber(newOrder.getTrackingNumber())
                .withReference(newOrder.getReference())
                .build();

        return CreateOrderResult.builder()
                .withOrder(orderModel)
                .build();
    }
}
