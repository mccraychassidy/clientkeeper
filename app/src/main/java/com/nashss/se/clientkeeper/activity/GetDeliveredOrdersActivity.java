package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.GetDeliveredOrdersRequest;
import com.nashss.se.clientkeeper.activity.results.GetDeliveredOrdersResult;
import com.nashss.se.clientkeeper.dynamodb.OrderDao;
import com.nashss.se.clientkeeper.dynamodb.models.Order;
import com.nashss.se.clientkeeper.models.OrderModel;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;

/**
 * Activity to get orders with a delivered date.
 */
public class GetDeliveredOrdersActivity {
    private final OrderDao orderDao;

    /**
     * Constructs a GetDeliveredOrdersActivity with the given OrderDao.
     *
     * @param orderDao interacts with the database
     */
    @Inject
    public GetDeliveredOrdersActivity(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    /**
     * Handles the request to get orders with a delivered date.
     *
     * @param request the request containing the user's email
     * @return the result of the delivered orders retrieval
     */
    public GetDeliveredOrdersResult handleRequest(GetDeliveredOrdersRequest request) {
        List<Order> deliveredOrders = orderDao.getOrdersWithDeliveredDate(request.getUserEmail());

        List<OrderModel> orderModels = deliveredOrders.stream()
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

        return GetDeliveredOrdersResult.builder()
                .withOrders(orderModels)
                .build();
    }
}
