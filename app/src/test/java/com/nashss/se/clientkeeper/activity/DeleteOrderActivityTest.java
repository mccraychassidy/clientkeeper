package com.nashss.se.clientkeeper.activity;

import com.nashss.se.clientkeeper.activity.requests.DeleteOrderRequest;
import com.nashss.se.clientkeeper.activity.results.DeleteOrderResult;
import com.nashss.se.clientkeeper.dynamodb.OrderDao;
import com.nashss.se.clientkeeper.exceptions.OrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class DeleteOrderActivityTest {
    @Mock
    private OrderDao orderDao;

    private DeleteOrderActivity deleteOrderActivity;

    @BeforeEach
    public void setup() {
        initMocks(this);
        deleteOrderActivity = new DeleteOrderActivity(orderDao);
    }

    @Test
    public void handleRequest_withValidRequest_deletesOrder() {
        // GIVEN
        DeleteOrderRequest request = DeleteOrderRequest.builder()
                .withUserEmail("user@example.com")
                .withOrderId("validOrderId")
                .build();

        doNothing().when(orderDao).deleteOrder("user@example.com", "validOrderId");

        // WHEN
        DeleteOrderResult result = deleteOrderActivity.handleRequest(request);

        // THEN
        assertNotNull(result);
        assertTrue(result.isSuccess());
        verify(orderDao).deleteOrder("user@example.com", "validOrderId");
    }

    @Test
    public void handleRequest_withNonExistentOrder_throwsOrderNotFoundException() {
        // GIVEN
        DeleteOrderRequest request = DeleteOrderRequest.builder()
                .withUserEmail("user@example.com")
                .withOrderId("nonExistentOrderId")
                .build();

        doThrow(new OrderNotFoundException("Order not found with orderId: nonExistentOrderId"))
                .when(orderDao).deleteOrder("user@example.com", "nonExistentOrderId");

        // WHEN
        DeleteOrderResult result = deleteOrderActivity.handleRequest(request);

        // THEN
        assertNotNull(result);
        assertFalse(result.isSuccess());
        verify(orderDao).deleteOrder("user@example.com", "nonExistentOrderId");
    }
}
