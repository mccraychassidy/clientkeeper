package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.GetUndeliveredOrdersRequest;
import com.nashss.se.clientkeeper.activity.results.GetUndeliveredOrdersResult;
import com.nashss.se.clientkeeper.dynamodb.OrderDao;
import com.nashss.se.clientkeeper.dynamodb.models.Order;
import com.nashss.se.clientkeeper.models.OrderModel;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;

/**
 * Activity to get orders without a delivered date.
 */
public class GetUndeliveredOrdersActivity {
    private final OrderDao orderDao;

    /**
     * Constructs a GetUndeliveredOrdersActivity with the given OrderDao.
     *
     * @param orderDao interacts with the database
     */
    @Inject
    public GetUndeliveredOrdersActivity(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    /**
     * Handles the request to get orders without a delivered date.
     *
     * @param request the request containing the user's email
     * @return the result of the undelivered orders retrieval
     */
    public GetUndeliveredOrdersResult handleRequest(GetUndeliveredOrdersRequest request) {
        List<Order> undeliveredOrders = orderDao.getOrdersWithoutDeliveredDate(request.getUserEmail());

        List<OrderModel> orderModels = undeliveredOrders.stream()
                .map(order -> OrderModel.builder()
                        .withUserEmail(order.getUserEmail())
                        .withOrderId(order.getOrderId())
                        .withClientId(order.getClientId())
                        .withClientName(order.getClientName())
                        .withItem(order.getItem())
                        .withShipped(order.getShipped())
                        .withPurchaseDate(order.getPurchaseDate().toString())
                        .withShippingService(order.getShippingService())
                        .withExpectedDate(order.getExpectedDate() != null ?
                                order.getExpectedDate().toString() : null)
                        .withDeliveredDate(order.getDeliveredDate() != null ?
                                order.getDeliveredDate().toString() : null)
                        .withTrackingNumber(order.getTrackingNumber())
                        .withReference(order.getReference())
                        .build())
                .collect(Collectors.toList());

        return GetUndeliveredOrdersResult.builder()
                .withOrders(orderModels)
                .build();
    }
}
