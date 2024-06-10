package com.nashss.se.clientkeeper.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDate;

/**
 * Represents a request to create a new order.
 */
@JsonDeserialize(builder = CreateOrderRequest.Builder.class)
public class CreateOrderRequest {
    private final String userEmail;
    private final String orderId;
    private final String clientId;
    private final String item;
    private final Boolean shipped;
    private final LocalDate purchaseDate;
    private final String shippingService;
    private final LocalDate expectedDate;
    private final LocalDate deliveredDate;
    private final String trackingNumber;
    private final String reference;

    /**
     * Private constructor to create a new instance of CreateOrderRequest.
     *
     * @param builder the Builder object containing the values to initialize the fields
     */
    private CreateOrderRequest(Builder builder) {
        this.userEmail = builder.userEmail;
        this.orderId = builder.orderId;
        this.clientId = builder.clientId;
        this.item = builder.item;
        this.shipped = builder.shipped;
        this.purchaseDate = builder.purchaseDate;
        this.shippingService = builder.shippingService;
        this.expectedDate = builder.expectedDate;
        this.deliveredDate = builder.deliveredDate;
        this.trackingNumber = builder.trackingNumber;
        this.reference = builder.reference;
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
     * Gets the user email.
     *
     * @return the user email
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Gets the order ID.
     *
     * @return the order ID
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Gets the client ID.
     *
     * @return the client ID
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Gets the item.
     *
     * @return the item
     */
    public String getItem() {
        return item;
    }

    /**
     * Gets the shipped status.
     *
     * @return the shipped status
     */
    public Boolean getShipped() {
        return shipped;
    }

    /**
     * Gets the purchase date.
     *
     * @return the purchase date
     */
    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * Gets the shipping service.
     *
     * @return the shipping service
     */
    public String getShippingService() {
        return shippingService;
    }

    /**
     * Gets the expected delivery date.
     *
     * @return the expected delivery date
     */
    public LocalDate getExpectedDate() {
        return expectedDate;
    }

    /**
     * Gets the delivered date.
     *
     * @return the delivered date
     */
    public LocalDate getDeliveredDate() {
        return deliveredDate;
    }

    /**
     * Gets the tracking number.
     *
     * @return the tracking number
     */
    public String getTrackingNumber() {
        return trackingNumber;
    }

    /**
     * Gets the reference.
     *
     * @return the reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * Returns a string representation of the CreateOrderRequest object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "CreateOrderRequest{" +
                "userEmail='" + userEmail + '\'' +
                ", orderId='" + orderId + '\'' +
                ", clientId='" + clientId + '\'' +
                ", item='" + item + '\'' +
                ", shipped=" + shipped +
                ", purchaseDate=" + purchaseDate +
                ", shippingService='" + shippingService + '\'' +
                ", expectedDate=" + expectedDate +
                ", deliveredDate=" + deliveredDate +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", reference='" + reference + '\'' +
                '}';
    }

    /**
     * Builder class for CreateOrderRequest instances.
     */
    @JsonPOJOBuilder
    public static class Builder {
        private String userEmail;
        private String orderId;
        private String clientId;
        private String item;
        private Boolean shipped;
        private LocalDate purchaseDate;
        private String shippingService;
        private LocalDate expectedDate;
        private LocalDate deliveredDate;
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
         * Sets the item.
         *
         * @param itemName the item
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
         * @param purchaseDateValue the purchase date
         * @return the Builder instance
         */
        public Builder withPurchaseDate(LocalDate purchaseDateValue) {
            this.purchaseDate = purchaseDateValue;
            return this;
        }

        /**
         * Sets the shipping service.
         *
         * @param shippingServiceName the shipping service
         * @return the Builder instance
         */
        public Builder withShippingService(String shippingServiceName) {
            this.shippingService = shippingServiceName;
            return this;
        }

        /**
         * Sets the expected delivery date.
         *
         * @param expectedDeliveryDate the expected delivery date
         * @return the Builder instance
         */
        public Builder withExpectedDate(LocalDate expectedDeliveryDate) {
            this.expectedDate = expectedDeliveryDate;
            return this;
        }

        /**
         * Sets the delivered date.
         *
         * @param deliveredDateValue the delivered date
         * @return the Builder instance
         */
        public Builder withDeliveredDate(LocalDate deliveredDateValue) {
            this.deliveredDate = deliveredDateValue;
            return this;
        }

        /**
         * Sets the tracking number.
         *
         * @param trackingNumberValue the tracking number
         * @return the Builder instance
         */
        public Builder withTrackingNumber(String trackingNumberValue) {
            this.trackingNumber = trackingNumberValue;
            return this;
        }

        /**
         * Sets the reference.
         *
         * @param referenceValue the reference
         * @return the Builder instance
         */
        public Builder withReference(String referenceValue) {
            this.reference = referenceValue;
            return this;
        }

        /**
         * Builds a new CreateOrderRequest instance with the specified values.
         *
         * @return a new CreateOrderRequest instance
         */
        public CreateOrderRequest build() {
            return new CreateOrderRequest(this);
        }
    }
}
