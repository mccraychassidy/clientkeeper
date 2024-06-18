package com.nashss.se.clientkeeper.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderModelTest {

    @Test
    public void testEquals_sameValues_shouldReturnTrue() {
        OrderModel order1 = OrderModel.builder()
                .withUserEmail("user@example.com")
                .withOrderId("orderId123")
                .withClientId("clientId456")
                .withClientName("Acme Corp")
                .withItem("Product X")
                .withShipped(true)
                .withPurchaseDate("2023-07-01")
                .withShippingService("FedEx")
                .withExpectedDate("2023-07-15")
                .withDeliveredDate("2023-07-14")
                .withTrackingNumber("TRACK123")
                .withReference("Ref123")
                .build();

        OrderModel order2 = OrderModel.builder()
                .withUserEmail("user@example.com")
                .withOrderId("orderId123")
                .withClientId("clientId456")
                .withClientName("Acme Corp")
                .withItem("Product X")
                .withShipped(true)
                .withPurchaseDate("2023-07-01")
                .withShippingService("FedEx")
                .withExpectedDate("2023-07-15")
                .withDeliveredDate("2023-07-14")
                .withTrackingNumber("TRACK123")
                .withReference("Ref123")
                .build();

        assertTrue(order1.equals(order2) && order2.equals(order1));
        assertEquals(order1.hashCode(), order2.hashCode());
    }

    @Test
    public void testEquals_differentValues_shouldReturnFalse() {
        OrderModel order1 = OrderModel.builder()
                .withUserEmail("user@example.com")
                .withOrderId("orderId123")
                .withClientId("clientId456")
                .build();

        OrderModel order2 = OrderModel.builder()
                .withUserEmail("user@example.com")
                .withOrderId("orderId123")
                .withClientId("differentClientId")
                .build();

        assertFalse(order1.equals(order2));
        assertNotEquals(order1.hashCode(), order2.hashCode());
    }

    @Test
    public void testBuilder_correctInitialization() {
        String userEmail = "user@example.com";
        String orderId = "orderId123";
        String clientId = "clientId456";
        String clientName = "Acme Corp";
        String item = "Product X";
        Boolean shipped = true;
        String purchaseDate = "2023-07-01";
        String shippingService = "FedEx";
        String expectedDate = "2023-07-15";
        String deliveredDate = "2023-07-14";
        String trackingNumber = "TRACK123";
        String reference = "Ref123";

        OrderModel order = OrderModel.builder()
                .withUserEmail(userEmail)
                .withOrderId(orderId)
                .withClientId(clientId)
                .withClientName(clientName)
                .withItem(item)
                .withShipped(shipped)
                .withPurchaseDate(purchaseDate)
                .withShippingService(shippingService)
                .withExpectedDate(expectedDate)
                .withDeliveredDate(deliveredDate)
                .withTrackingNumber(trackingNumber)
                .withReference(reference)
                .build();

        assertAll("Order should be set up correctly",
                () -> assertEquals(userEmail, order.getUserEmail()),
                () -> assertEquals(orderId, order.getOrderId()),
                () -> assertEquals(clientId, order.getClientId()),
                () -> assertEquals(clientName, order.getClientName()),
                () -> assertEquals(item, order.getItem()),
                () -> assertEquals(shipped, order.getShipped()),
                () -> assertEquals(purchaseDate, order.getPurchaseDate()),
                () -> assertEquals(shippingService, order.getShippingService()),
                () -> assertEquals(expectedDate, order.getExpectedDate()),
                () -> assertEquals(deliveredDate, order.getDeliveredDate()),
                () -> assertEquals(trackingNumber, order.getTrackingNumber()),
                () -> assertEquals(reference, order.getReference())
        );
    }
}
