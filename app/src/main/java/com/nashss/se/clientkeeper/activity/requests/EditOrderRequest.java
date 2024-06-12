package com.nashss.se.clientkeeper.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents a request to edit an order.
 */
@JsonDeserialize(builder = EditOrderRequest.Builder.class)
public class EditOrderRequest {
    private final String userEmail;
    private final String orderId;
    private final String clientId;
    private final String clientName;
    private final String item;
    private final Boolean shipped;
    private final String purchaseDate;
    private final String shippingService;
    private final String expectedDate;
    private final String deliveredDate;
    private final String trackingNumber;
    private final String reference;

    /**
     * Private constructor to create a new instance of EditOrderRequest.
     *
     * @param builder the Builder object containing the values to initialize the fields
     */
    private EditOrderRequest(Builder builder) {
        this.userEmail = builder.userEmail;
        this.orderId = builder.orderId;
        this.clientId = builder.clientId;
        this.clientName = builder.clientName;
        this.item = builder.item;
        this.shipped = builder.shipped;
        this.purchaseDate = builder.purchaseDate;
        this.shippingService = builder.shippingService;
        this.expectedDate = builder.expectedDate;
        this.deliveredDate = builder.deliveredDate;
        this.trackingNumber = builder.trackingNumber;
        this.reference = builder.reference;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getItem() {
        return item;
    }

    public Boolean getShipped() {
        return shipped;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getShippingService() {
        return shippingService;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public String getDeliveredDate() {
        return deliveredDate;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getReference() {
        return reference;
    }

    /**
     * Returns a new Builder instance.
     *
     * @return a new Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for EditOrderRequest instances.
     */
    @JsonPOJOBuilder
    public static class Builder {
        private String userEmail;
        private String orderId;
        private String clientId;
        private String clientName;
        private String item;
        private Boolean shipped;
        private String purchaseDate;
        private String shippingService;
        private String expectedDate;
        private String deliveredDate;
        private String trackingNumber;
        private String reference;

        public Builder withUserEmail(String email) {
            this.userEmail = email;
            return this;
        }

        public Builder withOrderId(String id) {
            this.orderId = id;
            return this;
        }

        public Builder withClientId(String id) {
            this.clientId = id;
            return this;
        }

        public Builder withClientName(String name) {
            this.clientName = name;
            return this;
        }

        public Builder withItem(String itemName) {
            this.item = itemName;
            return this;
        }

        public Builder withShipped(Boolean shippedStatus) {
            this.shipped = shippedStatus;
            return this;
        }

        public Builder withPurchaseDate(String purchaseDateValue) {
            this.purchaseDate = purchaseDateValue;
            return this;
        }

        public Builder withShippingService(String shippingServiceName) {
            this.shippingService = shippingServiceName;
            return this;
        }

        public Builder withExpectedDate(String expectedDeliveryDate) {
            this.expectedDate = expectedDeliveryDate;
            return this;
        }

        public Builder withDeliveredDate(String deliveredDateValue) {
            this.deliveredDate = deliveredDateValue;
            return this;
        }

        public Builder withTrackingNumber(String trackingNumberValue) {
            this.trackingNumber = trackingNumberValue;
            return this;
        }

        public Builder withReference(String referenceValue) {
            this.reference = referenceValue;
            return this;
        }

        public EditOrderRequest build() {
            return new EditOrderRequest(this);
        }
    }
}
