package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.CreateOrderRequest;
import com.nashss.se.clientkeeper.activity.results.CreateOrderResult;
import com.nashss.se.clientkeeper.dynamodb.OrderDao;
import com.nashss.se.clientkeeper.dynamodb.models.Order;
import com.nashss.se.clientkeeper.exceptions.InvalidAttributeValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreateOrderActivityTest {
    @Mock
    private OrderDao orderDao;

    private CreateOrderActivity createOrderActivity;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @BeforeEach
    public void setup() {
        initMocks(this);
        createOrderActivity = new CreateOrderActivity(orderDao);
    }

    @Test
    public void handleRequest_withValidRequest_createsOrder() {
        // GIVEN
        CreateOrderRequest request = CreateOrderRequest.builder()
                .withUserEmail("user@example.com")
                .withOrderId("validOrderId")
                .withClientId("validClientId")
                .withClientName("Client Name")
                .withItem("Sample Item")
                .withShipped(false)
                .withPurchaseDate("2024-06-01")
                .withShippingService("FedEx")
                .withExpectedDate("2024-06-10")
                .withDeliveredDate("2024-06-12")
                .withTrackingNumber("1234567890")
                .withReference("REF12345")
                .build();

        Order order = new Order();
        order.setUserEmail("user@example.com");
        order.setOrderId("validOrderId");
        order.setClientId("validClientId");
        order.setClientName("Client Name");
        order.setItem("Sample Item");
        order.setShipped(false);
        order.setPurchaseDate(LocalDate.parse("2024-06-01", DATE_FORMATTER));
        order.setShippingService("FedEx");
        order.setExpectedDate(LocalDate.parse("2024-06-10", DATE_FORMATTER));
        order.setDeliveredDate(LocalDate.parse("2024-06-12", DATE_FORMATTER));
        order.setTrackingNumber("1234567890");
        order.setReference("REF12345");

        when(orderDao.saveOrder(any(Order.class))).thenReturn(order);

        // WHEN
        CreateOrderResult result = createOrderActivity.handleRequest(request);

        // THEN
        assertNotNull(result);
        assertNotNull(result.getOrder());
        assertEquals("Client Name", result.getOrder().getClientName());
        assertEquals("Sample Item", result.getOrder().getItem());
        verify(orderDao).saveOrder(any(Order.class));
    }

    @Test
    public void handleRequest_withMissingAttributes_throwsInvalidAttributeValueException() {
        // GIVEN
        CreateOrderRequest request = CreateOrderRequest.builder()
                .withUserEmail("user@example.com")
                .withOrderId("validOrderId")
                .withClientId("validClientId")
                // Missing item
                .withShipped(false)
                .withPurchaseDate("2024-06-01")
                .withShippingService("FedEx")
                .withExpectedDate("2024-06-10")
                .withDeliveredDate("2024-06-12")
                .withTrackingNumber("1234567890")
                .withReference("REF12345")
                .build();

        // WHEN + THEN
        assertThrows(InvalidAttributeValueException.class, () -> createOrderActivity.handleRequest(request));
        verify(orderDao, never()).saveOrder(any(Order.class));
    }
}