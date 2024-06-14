package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.GetOrderRequest;
import com.nashss.se.clientkeeper.activity.results.GetOrderResult;
import com.nashss.se.clientkeeper.converters.ModelConverter;
import com.nashss.se.clientkeeper.dynamodb.OrderDao;
import com.nashss.se.clientkeeper.dynamodb.models.Order;
import com.nashss.se.clientkeeper.exceptions.OrderNotFoundException;
import com.nashss.se.clientkeeper.models.OrderModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Handles the retrieval of an order.
 */
public class GetOrderActivity {
    private final Logger log = LogManager.getLogger();
    private final OrderDao orderDao;

    /**
     * Constructs a GetOrderActivity with the given OrderDao.
     *
     * @param orderDao interacts with the database
     */
    @Inject
    public GetOrderActivity(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    /**
     * Handles the request to get an order.
     *
     * @param getOrderRequest the request containing order ID and user email
     * @return the result of the order retrieval
     * @throws OrderNotFoundException if the order is not found
     */
    public GetOrderResult handleRequest(final GetOrderRequest getOrderRequest) {
        log.info("Received GetOrderRequest {}", getOrderRequest);

        Order order = orderDao.getOrder(getOrderRequest.getUserEmail(), getOrderRequest.getOrderId());
        if (order == null) {
            throw new OrderNotFoundException("Order not found with orderId: " + getOrderRequest.getOrderId());
        }

        OrderModel orderModel = new ModelConverter().toOrderModel(order);

        return GetOrderResult.builder()
                .withOrder(orderModel)
                .build();
    }
}
