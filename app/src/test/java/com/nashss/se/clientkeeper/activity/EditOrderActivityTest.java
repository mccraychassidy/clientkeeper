package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.EditOrderRequest;
import com.nashss.se.clientkeeper.activity.results.EditOrderResult;
import com.nashss.se.clientkeeper.dynamodb.OrderDao;
import com.nashss.se.clientkeeper.dynamodb.models.Order;
import com.nashss.se.clientkeeper.exceptions.OrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class EditOrderActivityTest {
    @Mock
    private OrderDao orderDao;

    private EditOrderActivity editOrderActivity;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @BeforeEach
    public void setup() {
        initMocks(this);
        editOrderActivity = new EditOrderActivity(orderDao);
    }

    @Test
    public void handleRequest_withValidRequest_editsOrder() {
        // GIVEN
        EditOrderRequest request = EditOrderRequest.builder()
                .withUserEmail("user@example.com")
                .withOrderId("validOrderId")
                .withClientId("validClientId")
                .withClientName("John Doe Updated")
                .withItem("Updated Item")
                .withShipped(true)
                .withPurchaseDate("2024-01-01")
                .withShippingService("Updated Shipping Service")
                .withExpectedDate("2024-01-15")
                .withDeliveredDate("2024-01-20")
                .withTrackingNumber("Updated Tracking Number")
                .withReference("Updated Reference")
                .build();

        Order existingOrder = new Order();
        existingOrder.setUserEmail("user@example.com");
        existingOrder.setOrderId("validOrderId");
        existingOrder.setClientId("validClientId");
        existingOrder.setClientName("John Doe");
        existingOrder.setItem("Original Item");
        existingOrder.setShipped(false);
        existingOrder.setPurchaseDate(LocalDate.parse("2024-01-01", DATE_FORMATTER));
        existingOrder.setShippingService("Original Shipping Service");
        existingOrder.setExpectedDate(LocalDate.parse("2024-01-15", DATE_FORMATTER));
        existingOrder.setDeliveredDate(LocalDate.parse("2024-01-20", DATE_FORMATTER));
        existingOrder.setTrackingNumber("Original Tracking Number");
        existingOrder.setReference("Original Reference");

        when(orderDao.getOrder("user@example.com", "validOrderId")).thenReturn(existingOrder);

        // WHEN
        EditOrderResult result = editOrderActivity.handleRequest(request);

        // THEN
        assertNotNull(result);
        assertNotNull(result.getOrder());
        assertEquals("John Doe Updated", result.getOrder().getClientName());
        assertEquals("Updated Item", result.getOrder().getItem());
        verify(orderDao).getOrder("user@example.com", "validOrderId");
        verify(orderDao).saveOrder(any(Order.class));
    }

    @Test
    public void handleRequest_withInvalidOrderId_throwsOrderNotFoundException() {
        // GIVEN
        EditOrderRequest request = EditOrderRequest.builder()
                .withUserEmail("user@example.com")
                .withOrderId("invalidOrderId")
                .withClientId("validClientId")
                .withClientName("John Doe Updated")
                .withItem("Updated Item")
                .withShipped(true)
                .withPurchaseDate("2024-01-01")
                .withShippingService("Updated Shipping Service")
                .withExpectedDate("2024-01-15")
                .withDeliveredDate("2024-01-20")
                .withTrackingNumber("Updated Tracking Number")
                .withReference("Updated Reference")
                .build();

        when(orderDao.getOrder("user@example.com", "invalidOrderId")).thenThrow(new OrderNotFoundException("Order not found with orderId: invalidOrderId"));

        // WHEN + THEN
        assertThrows(OrderNotFoundException.class, () -> editOrderActivity.handleRequest(request));
        verify(orderDao).getOrder("user@example.com", "invalidOrderId");
        verify(orderDao, never()).saveOrder(any(Order.class));
    }

    @Test
    public void handleRequest_withNonExistentOrder_throwsOrderNotFoundException() {
        // GIVEN
        EditOrderRequest request = EditOrderRequest.builder()
                .withUserEmail("user@example.com")
                .withOrderId("nonExistentOrderId")
                .withClientId("validClientId")
                .withClientName("John Doe Updated")
                .withItem("Updated Item")
                .withShipped(true)
                .withPurchaseDate("2024-01-01")
                .withShippingService("Updated Shipping Service")
                .withExpectedDate("2024-01-15")
                .withDeliveredDate("2024-01-20")
                .withTrackingNumber("Updated Tracking Number")
                .withReference("Updated Reference")
                .build();

        when(orderDao.getOrder("user@example.com", "nonExistentOrderId")).thenReturn(null);

        // WHEN + THEN
        assertThrows(OrderNotFoundException.class, () -> editOrderActivity.handleRequest(request));
        verify(orderDao).getOrder("user@example.com", "nonExistentOrderId");
        verify(orderDao, never()).saveOrder(any(Order.class));
    }
}
