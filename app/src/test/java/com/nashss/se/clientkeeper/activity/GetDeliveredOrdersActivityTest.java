package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.GetDeliveredOrdersRequest;
import com.nashss.se.clientkeeper.activity.results.GetDeliveredOrdersResult;
import com.nashss.se.clientkeeper.dynamodb.OrderDao;
import com.nashss.se.clientkeeper.dynamodb.models.Order;
import com.nashss.se.clientkeeper.models.OrderModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetDeliveredOrdersActivityTest {
    @Mock
    private OrderDao orderDao;

    private GetDeliveredOrdersActivity getDeliveredOrdersActivity;

    @BeforeEach
    public void setup() {
        initMocks(this);
        getDeliveredOrdersActivity = new GetDeliveredOrdersActivity(orderDao);
    }

    @Test
    public void handleRequest_withValidRequest_returnsDeliveredOrders() {
        // GIVEN
        GetDeliveredOrdersRequest request = GetDeliveredOrdersRequest.builder()
                .withUserEmail("user@example.com")
                .build();

        Order order1 = new Order();
        order1.setUserEmail("user@example.com");
        order1.setOrderId("order1");
        order1.setClientId("client1");
        order1.setClientName("Client One");
        order1.setItem("Item1");
        order1.setShipped(false);
        order1.setPurchaseDate(LocalDate.of(2024, 6, 1));
        order1.setExpectedDate(LocalDate.of(2024, 6, 10));
        order1.setDeliveredDate(LocalDate.of(2024, 6, 12));
        order1.setTrackingNumber("1234567890");
        order1.setReference("REF12345");

        Order order2 = new Order();
        order2.setUserEmail("user@example.com");
        order2.setOrderId("order2");
        order2.setClientId("client2");
        order2.setClientName("Client Two");
        order2.setItem("Item2");
        order2.setShipped(true);
        order2.setPurchaseDate(LocalDate.of(2024, 6, 2));
        order2.setExpectedDate(LocalDate.of(2024, 6, 11));
        order2.setDeliveredDate(LocalDate.of(2024, 6, 13));
        order2.setTrackingNumber("0987654321");
        order2.setReference("REF54321");

        when(orderDao.getOrdersWithDeliveredDate("user@example.com")).thenReturn(Arrays.asList(order1, order2));

        // WHEN
        GetDeliveredOrdersResult result = getDeliveredOrdersActivity.handleRequest(request);

        // THEN
        assertNotNull(result);
        assertNotNull(result.getOrders());
        assertEquals(2, result.getOrders().size());

        OrderModel orderModel1 = result.getOrders().get(0);
        OrderModel orderModel2 = result.getOrders().get(1);

        assertEquals("Client One", orderModel1.getClientName());
        assertEquals("Client Two", orderModel2.getClientName());
        assertEquals("Item1", orderModel1.getItem());
        assertEquals("Item2", orderModel2.getItem());
        verify(orderDao).getOrdersWithDeliveredDate("user@example.com");
    }

    @Test
    public void handleRequest_withNoDeliveredOrders_returnsEmptyList() {
        // GIVEN
        GetDeliveredOrdersRequest request = GetDeliveredOrdersRequest.builder()
                .withUserEmail("user@example.com")
                .build();

        when(orderDao.getOrdersWithDeliveredDate("user@example.com")).thenReturn(List.of());

        // WHEN
        GetDeliveredOrdersResult result = getDeliveredOrdersActivity.handleRequest(request);

        // THEN
        assertNotNull(result);
        assertNotNull(result.getOrders());
        assertTrue(result.getOrders().isEmpty());
        verify(orderDao).getOrdersWithDeliveredDate("user@example.com");
    }
}
