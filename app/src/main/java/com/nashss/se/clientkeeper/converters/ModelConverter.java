package com.nashss.se.clientkeeper.converters;

import com.nashss.se.clientkeeper.dynamodb.models.Client;
import com.nashss.se.clientkeeper.dynamodb.models.Order;
import com.nashss.se.clientkeeper.models.ClientModel;
import com.nashss.se.clientkeeper.models.OrderModel;

/**
 * Converts between Data and API models.
 */
public class ModelConverter {
    private final DateConverter dateConverter = new DateConverter();

    /**
     * Converts a provided {@link Client} into a {@link ClientModel} representation.
     *
     * @param client the client to convert
     * @return the converted client
     */
    public ClientModel toClientModel(Client client) {
        return ClientModel.builder()
                .withUserEmail(client.getUserEmail())
                .withClientId(client.getClientId())
                .withClientName(client.getClientName())
                .withClientEmail(client.getClientEmail())
                .withClientPhone(client.getClientPhone())
                .withClientAddress(client.getClientAddress())
                .withClientMemberSince(dateConverter.convert(client.getClientMemberSince()))
                .build();
    }

    /**
     * Converts a provided {@link Order} into a {@link OrderModel} representation.
     *
     * @param order the order to convert
     * @return the converted order
     */
    public OrderModel toOrderModel(Order order) {
        return OrderModel.builder()
                .withUserEmail(order.getUserEmail())
                .withOrderId(order.getOrderId())
                .withClientId(order.getClientId())
                .withClientName(order.getClientName())
                .withItem(order.getItem())
                .withShipped(order.getShipped())
                .withPurchaseDate(dateConverter.convert(order.getPurchaseDate()))
                .withShippingService(order.getShippingService())
                .withExpectedDate(order.getExpectedDate() != null ?
                        dateConverter.convert(order.getExpectedDate()) : null)
                .withDeliveredDate(order.getDeliveredDate() != null ?
                        dateConverter.convert(order.getDeliveredDate()) : null)
                .withTrackingNumber(order.getTrackingNumber())
                .withReference(order.getReference())
                .build();
    }
}
