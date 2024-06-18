package com.nashss.se.clientkeeper.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.clientkeeper.dynamodb.models.Order;
import com.nashss.se.clientkeeper.exceptions.InvalidAttributeValueException;
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

public class OrderDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;

    private OrderDao orderDao;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    @BeforeEach
    public void setup() {
        initMocks(this);
        orderDao = new OrderDao(dynamoDBMapper);
    }

    @Test
    public void getOrder_withValidOrderId_returnsOrder() {
        // GIVEN
        String userEmail = "user@example.com";
        String orderId = "validOrderId";
        Order expectedOrder = new Order();
        expectedOrder.setUserEmail(userEmail);
        expectedOrder.setOrderId(orderId);

        when(dynamoDBMapper.load(Order.class, userEmail, orderId)).thenReturn(expectedOrder);

        // WHEN
        Order order = orderDao.getOrder(userEmail, orderId);

        // THEN
        assertNotNull(order);
        assertEquals(expectedOrder, order);
        verify(dynamoDBMapper).load(Order.class, userEmail, orderId);
    }

    @Test
    public void getOrder_withInvalidOrderId_throwsOrderNotFoundException() {
        // GIVEN
        String userEmail = "user@example.com";
        String orderId = "invalidOrderId";

        when(dynamoDBMapper.load(Order.class, userEmail, orderId)).thenReturn(null);

        // WHEN + THEN
        assertThrows(OrderNotFoundException.class, () -> orderDao.getOrder(userEmail, orderId));
        verify(dynamoDBMapper).load(Order.class, userEmail, orderId);
    }

    @Test
    public void saveOrder_withValidOrder_savesOrder() {
        // GIVEN
        Order order = new Order();
        order.setUserEmail("user@example.com");
        order.setOrderId("validOrderId");
        order.setClientId("clientId");
        order.setItem("item");
        order.setShipped(true);
        order.setPurchaseDate(LocalDate.parse("01-01-2024", DATE_FORMATTER));
        order.setShippingService("service");
        order.setExpectedDate(LocalDate.parse("01-10-2024", DATE_FORMATTER));
        order.setDeliveredDate(LocalDate.parse("01-15-2024", DATE_FORMATTER));
        order.setTrackingNumber("trackingNumber");
        order.setReference("reference");

        // WHEN
        Order result = orderDao.saveOrder(order);

        // THEN
        verify(dynamoDBMapper).save(order);
        assertEquals(order, result);
    }

    @Test
    public void saveOrder_withMissingAttributes_throwsInvalidAttributeValueException() {
        // GIVEN
        Order order = new Order();
        order.setUserEmail("user@example.com");

        // WHEN + THEN
        assertThrows(InvalidAttributeValueException.class, () -> orderDao.saveOrder(order));
    }

    @Test
    public void deleteOrder_withValidOrderId_deletesOrder() {
        // GIVEN
        String userEmail = "user@example.com";
        String orderId = "validOrderId";
        Order order = new Order();
        order.setUserEmail(userEmail);
        order.setOrderId(orderId);

        when(dynamoDBMapper.load(Order.class, userEmail, orderId)).thenReturn(order);

        // WHEN
        orderDao.deleteOrder(userEmail, orderId);

        // THEN
        verify(dynamoDBMapper).delete(order);
    }

    @Test
    public void deleteOrder_withInvalidOrderId_throwsOrderNotFoundException() {
        // GIVEN
        String userEmail = "user@example.com";
        String orderId = "invalidOrderId";

        when(dynamoDBMapper.load(Order.class, userEmail, orderId)).thenReturn(null);

        // WHEN + THEN
        assertThrows(OrderNotFoundException.class, () -> orderDao.deleteOrder(userEmail, orderId));
    }
}

