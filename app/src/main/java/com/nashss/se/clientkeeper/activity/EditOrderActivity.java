package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.EditOrderRequest;
import com.nashss.se.clientkeeper.activity.results.EditOrderResult;
import com.nashss.se.clientkeeper.dynamodb.OrderDao;
import com.nashss.se.clientkeeper.dynamodb.models.Order;
import com.nashss.se.clientkeeper.exceptions.OrderNotFoundException;
import com.nashss.se.clientkeeper.models.OrderModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.time.LocalDate;

/**
 * Handles the editing of an order.
 */
public class EditOrderActivity {
    private final Logger log = LogManager.getLogger();
    private final OrderDao orderDao;

    @Inject
    public EditOrderActivity(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public EditOrderResult handleRequest(final EditOrderRequest request) {
        log.info("Received EditOrderRequest for orderId: {}", request.getOrderId());

        Order existingOrder = orderDao.getOrder(request.getUserEmail(), request.getOrderId());

        if (existingOrder == null) {
            throw new OrderNotFoundException("Order not found with orderId: " + request.getOrderId());
        }

        existingOrder.setClientName(request.getClientName());
        existingOrder.setItem(request.getItem());
        existingOrder.setShipped(request.getShipped());
        existingOrder.setPurchaseDate(LocalDate.parse(request.getPurchaseDate()));
        existingOrder.setShippingService(request.getShippingService());
        existingOrder.setExpectedDate(request.getExpectedDate() != null ? LocalDate.parse(request.getExpectedDate()) : null);

        if (request.getDeliveredDate() != null && !request.getDeliveredDate().isEmpty()) {
            existingOrder.setDeliveredDate(LocalDate.parse(request.getDeliveredDate()));
        } else {
            existingOrder.setDeliveredDate(null);
        }

        existingOrder.setTrackingNumber(request.getTrackingNumber());
        existingOrder.setReference(request.getReference());

        orderDao.saveOrder(existingOrder);

        OrderModel updatedOrderModel = OrderModel.builder()
                .withUserEmail(existingOrder.getUserEmail())
                .withOrderId(existingOrder.getOrderId())
                .withClientId(existingOrder.getClientId())
                .withClientName(existingOrder.getClientName())
                .withItem(existingOrder.getItem())
                .withShipped(existingOrder.getShipped())
                .withPurchaseDate(existingOrder.getPurchaseDate().toString())
                .withShippingService(existingOrder.getShippingService())
                .withExpectedDate(existingOrder.getExpectedDate() != null ? existingOrder.getExpectedDate().toString() : null)
                .withDeliveredDate(existingOrder.getDeliveredDate() != null ? existingOrder.getDeliveredDate().toString() : null)
                .withTrackingNumber(existingOrder.getTrackingNumber())
                .withReference(existingOrder.getReference())
                .build();

        return EditOrderResult.builder()
                .withOrder(updatedOrderModel)
                .build();
    }
}
