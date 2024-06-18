package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.GetOrdersByClientIdRequest;
import com.nashss.se.clientkeeper.activity.results.GetOrdersByClientIdResult;
import com.nashss.se.clientkeeper.dynamodb.OrderDao;
import com.nashss.se.clientkeeper.dynamodb.models.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetOrdersByClientIdActivityTest {
    @Mock
    private OrderDao orderDao;

    private GetOrdersByClientIdActivity getOrdersByClientIdActivity;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @BeforeEach
    public void setup() {
        initMocks(this);
        getOrdersByClientIdActivity = new GetOrdersByClientIdActivity(orderDao);
    }

    @Test
    public void handleRequest_withValidClientId_returnsOrders() {
        // GIVEN
        GetOrdersByClientIdRequest request = GetOrdersByClientIdRequest.builder()
                .withClientId("validClientId")
                .build();

        Order order = new Order();
        order.setUserEmail("user@example.com");
        order.setOrderId("orderId1");
        order.setClientId("validClientId");
        order.setClientName("Jane Doe");
        order.setItem("Item XYZ");
        order.setShipped(true);
        order.setPurchaseDate(LocalDate.parse("2024-01-27", DATE_FORMATTER));
        order.setShippingService("UPS");
        order.setExpectedDate(LocalDate.parse("2024-02-27", DATE_FORMATTER));
        order.setDeliveredDate(null);  // Assuming not yet delivered

        List<Order> orders = Arrays.asList(order);

        when(orderDao.getOrdersByClientId("validClientId")).thenReturn(orders);

        // WHEN
        GetOrdersByClientIdResult result = getOrdersByClientIdActivity.handleRequest(request);

        // THEN
        assertNotNull(result);
        assertFalse(result.getOrders().isEmpty());
        assertEquals("Jane Doe", result.getOrders().get(0).getClientName());
        verify(orderDao).getOrdersByClientId("validClientId");
    }

    @Test
    public void handleRequest_withInvalidClientId_returnsEmptyList() {
        // GIVEN
        GetOrdersByClientIdRequest request = GetOrdersByClientIdRequest.builder()
                .withClientId("invalidClientId")
                .build();

        when(orderDao.getOrdersByClientId("invalidClientId")).thenReturn(Arrays.asList());

        // WHEN
        GetOrdersByClientIdResult result = getOrdersByClientIdActivity.handleRequest(request);

        // THEN
        assertNotNull(result);
        assertTrue(result.getOrders().isEmpty());
        verify(orderDao).getOrdersByClientId("invalidClientId");
    }
}
