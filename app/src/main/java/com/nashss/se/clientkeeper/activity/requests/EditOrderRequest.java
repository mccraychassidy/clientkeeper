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

        /**
         * Sets the user email.
         *
         * @param email the user email
         * @return the Builder instance
         */
        public Builder withUserEmail(String email) {
            this.userEmail = email;
            return this;
        }

        /**
         * Sets the order ID.
         *
         * @param id the order ID
         * @return the Builder instance
         */
        public Builder withOrderId(String id) {
            this.orderId = id;
            return this;
        }

        /**
         * Sets the client ID.
         *
         * @param id the client ID
         * @return the Builder instance
         */
        public Builder withClientId(String id) {
            this.clientId = id;
            return this;
        }

        /**
         * Sets the client name.
         *
         * @param name the client name
         * @return the Builder instance
         */
        public Builder withClientName(String name) {
            this.clientName = name;
            return this;
        }

        /**
         * Sets the item.
         *
         * @param itemName the item name
         * @return the Builder instance
         */
        public Builder withItem(String itemName) {
            this.item = itemName;
            return this;
        }

        /**
         * Sets the shipped status.
         *
         * @param shippedStatus the shipped status
         * @return the Builder instance
         */
        public Builder withShipped(Boolean shippedStatus) {
            this.shipped = shippedStatus;
            return this;
        }

        /**
         * Sets the purchase date.
         *
         * @param purchaseDateValue the purchase date value
         * @return the Builder instance
         */
        public Builder withPurchaseDate(String purchaseDateValue) {
            this.purchaseDate = purchaseDateValue;
            return this;
        }

        /**
         * Sets the shipping service.
         *
         * @param shippingServiceName the shipping service name
         * @return the Builder instance
         */
        public Builder withShippingService(String shippingServiceName) {
            this.shippingService = shippingServiceName;
            return this;
        }

        /**
         * Sets the expected date.
         *
         * @param expectedDeliveryDate the expected delivery date
         * @return the Builder instance
         */
        public Builder withExpectedDate(String expectedDeliveryDate) {
            this.expectedDate = expectedDeliveryDate;
            return this;
        }

        /**
         * Sets the delivered date.
         *
         * @param deliveredDateValue the delivered date value
         * @return the Builder instance
         */
        public Builder withDeliveredDate(String deliveredDateValue) {
            this.deliveredDate = deliveredDateValue;
            return this;
        }

        /**
         * Sets the tracking number.
         *
         * @param trackingNumberValue the tracking number value
         * @return the Builder instance
         */
        public Builder withTrackingNumber(String trackingNumberValue) {
            this.trackingNumber = trackingNumberValue;
            return this;
        }

        /**
         * Sets the reference.
         *
         * @param referenceValue the reference value
         * @return the Builder instance
         */
        public Builder withReference(String referenceValue) {
            this.reference = referenceValue;
            return this;
        }

        /**
         * Builds a new EditOrderRequest instance with the specified values.
         *
         * @return a new EditOrderRequest instance
         */
        public EditOrderRequest build() {
            return new EditOrderRequest(this);
        }
    }
}
