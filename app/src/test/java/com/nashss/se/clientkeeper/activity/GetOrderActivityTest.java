package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.GetOrderRequest;
import com.nashss.se.clientkeeper.activity.results.GetOrderResult;
import com.nashss.se.clientkeeper.dynamodb.OrderDao;
import com.nashss.se.clientkeeper.dynamodb.models.Order;
import com.nashss.se.clientkeeper.exceptions.OrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetOrderActivityTest {
    @Mock
    private OrderDao orderDao;

    private GetOrderActivity getOrderActivity;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @BeforeEach
    public void setup() {
        initMocks(this);
        getOrderActivity = new GetOrderActivity(orderDao);
    }

    @Test
    public void handleRequest_withValidRequest_returnsOrder() {
        // GIVEN
        GetOrderRequest request = GetOrderRequest.builder()
                .withUserEmail("user@example.com")
                .withOrderId("validOrderId")
                .build();

        Order order = new Order();
        order.setUserEmail("user@example.com");
        order.setOrderId("validOrderId");
        order.setClientName("Jane Doe");
        order.setItem("Item XYZ");
        order.setShipped(true);
        order.setPurchaseDate(LocalDate.parse("2024-01-27", DATE_FORMATTER));
        order.setShippingService("UPS");
        order.setExpectedDate(LocalDate.parse("2024-02-27", DATE_FORMATTER));
        order.setDeliveredDate(null);  // Assuming not yet delivered

        when(orderDao.getOrder("user@example.com", "validOrderId")).thenReturn(order);

        // WHEN
        GetOrderResult result = getOrderActivity.handleRequest(request);

        // THEN
        assertNotNull(result);
        assertNotNull(result.getOrder());
        assertEquals("Jane Doe", result.getOrder().getClientName());
        verify(orderDao).getOrder("user@example.com", "validOrderId");
    }

    @Test
    public void handleRequest_withInvalidOrderId_throwsOrderNotFoundException() {
        // GIVEN
        GetOrderRequest request = GetOrderRequest.builder()
                .withUserEmail("user@example.com")
                .withOrderId("invalidOrderId")
                .build();

        when(orderDao.getOrder("user@example.com", "invalidOrderId")).thenThrow(new OrderNotFoundException("Order not found with orderId: invalidOrderId"));

        // WHEN + THEN
        assertThrows(OrderNotFoundException.class, () -> getOrderActivity.handleRequest(request));
        verify(orderDao).getOrder("user@example.com", "invalidOrderId");
    }
}
