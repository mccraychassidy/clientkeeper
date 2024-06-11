package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.CreateOrderRequest;
import com.nashss.se.clientkeeper.activity.results.CreateOrderResult;
import com.nashss.se.clientkeeper.dynamodb.OrderDao;
import com.nashss.se.clientkeeper.dynamodb.models.Order;
import com.nashss.se.clientkeeper.exceptions.InvalidAttributeValueException;
import com.nashss.se.clientkeeper.models.OrderModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;
import javax.inject.Inject;


/**
 * Handles the creation of a new order.
 */
public class CreateOrderActivity {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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

        if (createOrderRequest.getItem() == null || createOrderRequest.getPurchaseDate() == null) {
            throw new InvalidAttributeValueException("Missing required attribute(s)");
        }

        LocalDate purchaseDate;
        LocalDate expectedDate = null;
        LocalDate deliveredDate = null;

        try {
            purchaseDate = LocalDate.parse(createOrderRequest.getPurchaseDate(), DATE_FORMATTER);
            if (createOrderRequest.getExpectedDate() != null && !createOrderRequest.getExpectedDate().isEmpty()) {
                expectedDate = LocalDate.parse(createOrderRequest.getExpectedDate(), DATE_FORMATTER);
            }
            if (createOrderRequest.getDeliveredDate() != null && !createOrderRequest.getDeliveredDate().isEmpty()) {
                deliveredDate = LocalDate.parse(createOrderRequest.getDeliveredDate(), DATE_FORMATTER);
            }
        } catch (DateTimeParseException e) {
            throw new InvalidAttributeValueException("Invalid date format. Required format: yyyy-MM-dd");
        }

        String orderId = createOrderRequest.getOrderId() != null ?
                createOrderRequest.getOrderId() : UUID.randomUUID().toString();

        Order newOrder = new Order();
        newOrder.setUserEmail(createOrderRequest.getUserEmail());
        newOrder.setOrderId(orderId);
        newOrder.setClientId(createOrderRequest.getClientId());
        newOrder.setItem(createOrderRequest.getItem());
        newOrder.setShipped(createOrderRequest.getShipped());
        newOrder.setPurchaseDate(purchaseDate);
        newOrder.setShippingService(createOrderRequest.getShippingService());
        newOrder.setExpectedDate(expectedDate);
        newOrder.setDeliveredDate(deliveredDate);
        newOrder.setTrackingNumber(createOrderRequest.getTrackingNumber());
        newOrder.setReference(createOrderRequest.getReference());
        orderDao.saveOrder(newOrder);

        OrderModel orderModel = OrderModel.builder()
                .withUserEmail(newOrder.getUserEmail())
                .withOrderId(newOrder.getOrderId())
                .withClientId(newOrder.getClientId())
                .withItem(newOrder.getItem())
                .withShipped(newOrder.getShipped())
                .withPurchaseDate(newOrder.getPurchaseDate().format(DATE_FORMATTER))
                .withShippingService(newOrder.getShippingService())
                .withExpectedDate(newOrder.getExpectedDate() != null ?
                        newOrder.getExpectedDate().format(DATE_FORMATTER) : null)
                .withDeliveredDate(newOrder.getDeliveredDate() != null ?
                        newOrder.getDeliveredDate().format(DATE_FORMATTER) : null)
                .withTrackingNumber(newOrder.getTrackingNumber())
                .withReference(newOrder.getReference())
                .build();

        return CreateOrderResult.builder()
                .withOrder(orderModel)
                .build();
    }
}