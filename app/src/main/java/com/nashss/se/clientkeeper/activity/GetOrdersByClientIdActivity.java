package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.GetOrdersByClientIdRequest;
import com.nashss.se.clientkeeper.activity.results.GetOrdersByClientIdResult;
import com.nashss.se.clientkeeper.dynamodb.OrderDao;
import com.nashss.se.clientkeeper.dynamodb.models.Order;
import com.nashss.se.clientkeeper.models.OrderModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;

/**
 * Handles the retrieval of orders by clientId.
 */
public class GetOrdersByClientIdActivity {
    private final Logger log = LogManager.getLogger();
    private final OrderDao orderDao;

    /**
     * Constructs a GetOrdersByClientIdActivity with the given OrderDao.
     *
     * @param orderDao interacts with the database
     */
    @Inject
    public GetOrdersByClientIdActivity(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    /**
     * Handles the request to get orders by clientId.
     *
     * @param request the request containing the clientId
     * @return the result of the orders retrieval
     */
    public GetOrdersByClientIdResult handleRequest(final GetOrdersByClientIdRequest request) {
        log.info("Received GetOrdersByClientIdRequest {}", request);

        List<Order> orders = orderDao.getOrdersByClientId(request.getClientId());
        List<OrderModel> orderModels = orders.stream().map(order -> OrderModel.builder()
                .withUserEmail(order.getUserEmail())
                .withOrderId(order.getOrderId())
                .withClientId(order.getClientId())
                .withClientName(order.getClientName())
                .withItem(order.getItem())
                .withShipped(order.getShipped())
                .withPurchaseDate(order.getPurchaseDate().toString())
                .withShippingService(order.getShippingService())
                .withExpectedDate(order.getExpectedDate() != null ? order.getExpectedDate().toString() : null)
                .withDeliveredDate(order.getDeliveredDate() != null ? order.getDeliveredDate().toString() : null)
                .withTrackingNumber(order.getTrackingNumber())
                .withReference(order.getReference())
                .build()).collect(Collectors.toList());

        return GetOrdersByClientIdResult.builder()
                .withOrders(orderModels)
                .build();
    }
}
