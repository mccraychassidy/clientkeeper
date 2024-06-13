package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.DeleteOrderRequest;
import com.nashss.se.clientkeeper.activity.results.DeleteOrderResult;
import com.nashss.se.clientkeeper.dynamodb.OrderDao;
import com.nashss.se.clientkeeper.exceptions.OrderNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Handles the deletion of an order.
 */
public class DeleteOrderActivity {
    private final Logger log = LogManager.getLogger();
    private final OrderDao orderDao;

    /**
     * Constructs a DeleteOrderActivity with the given OrderDao.
     *
     * @param orderDao interacts with the database
     */
    @Inject
    public DeleteOrderActivity(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    /**
     * Handles the request to delete an order.
     *
     * @param deleteOrderRequest the request containing order information
     * @return the result of the order deletion
     */
    public DeleteOrderResult handleRequest(final DeleteOrderRequest deleteOrderRequest) {
        log.info("Received DeleteOrderRequest {}", deleteOrderRequest);

        try {
            orderDao.deleteOrder(deleteOrderRequest.getUserEmail(), deleteOrderRequest.getOrderId());
            return DeleteOrderResult.builder()
                    .withSuccess(true)
                    .build();
        } catch (OrderNotFoundException e) {
            log.error("Order not found with orderId: {}", deleteOrderRequest.getOrderId());
            return DeleteOrderResult.builder()
                    .withSuccess(false)
                    .build();
        }
    }
}
