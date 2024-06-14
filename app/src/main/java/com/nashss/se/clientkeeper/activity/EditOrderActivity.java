package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.EditOrderRequest;
import com.nashss.se.clientkeeper.activity.results.EditOrderResult;
import com.nashss.se.clientkeeper.converters.ModelConverter;
import com.nashss.se.clientkeeper.dynamodb.OrderDao;
import com.nashss.se.clientkeeper.dynamodb.models.Order;
import com.nashss.se.clientkeeper.exceptions.OrderNotFoundException;
import com.nashss.se.clientkeeper.models.OrderModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import javax.inject.Inject;

/**
 * Handles the editing of an order.
 */
public class EditOrderActivity {
    private final Logger log = LogManager.getLogger();
    private final OrderDao orderDao;

    /**
     * Constructs an EditOrderActivity with the given OrderDao.
     *
     * @param orderDao interacts with the database
     */
    @Inject
    public EditOrderActivity(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    /**
     * Handles the request to edit an order.
     *
     * @param request the request containing the order information to be edited
     * @return the result of the order edit
     * @throws OrderNotFoundException if the order is not found
     */
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
        existingOrder.setExpectedDate(request.getExpectedDate() != null ?
                LocalDate.parse(request.getExpectedDate()) : null);

        if (request.getDeliveredDate() != null && !request.getDeliveredDate().isEmpty()) {
            existingOrder.setDeliveredDate(LocalDate.parse(request.getDeliveredDate()));
        } else {
            existingOrder.setDeliveredDate(null);
        }

        existingOrder.setTrackingNumber(request.getTrackingNumber());
        existingOrder.setReference(request.getReference());

        orderDao.saveOrder(existingOrder);

        OrderModel updatedOrderModel = new ModelConverter().toOrderModel(existingOrder);

        return EditOrderResult.builder()
                .withOrder(updatedOrderModel)
                .build();
    }
}
