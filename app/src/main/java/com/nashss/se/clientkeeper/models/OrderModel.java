package com.nashss.se.clientkeeper.models;

import java.util.Objects;

/**
 * Represents an order.
 */
public class OrderModel {
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
     * Private constructor for creating an OrderModel instance.
     *
     * @param userEmail the user's email
     * @param orderId the order's ID
     * @param clientId the client's ID
     * @param item the item
     * @param shipped the shipped status
     * @param purchaseDate the purchase date
     * @param shippingService the shipping service
     * @param expectedDate the expected delivery date
     * @param deliveredDate the delivered date
     * @param trackingNumber the tracking number
     * @param reference the reference
     */
    private OrderModel(String userEmail, String orderId, String clientId, String clientName,
                       String item, Boolean shipped,
                       String purchaseDate, String shippingService, String expectedDate,
                       String deliveredDate, String trackingNumber, String reference) {
        this.userEmail = userEmail;
        this.orderId = orderId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.item = item;
        this.shipped = shipped;
        this.purchaseDate = purchaseDate;
        this.shippingService = shippingService;
        this.expectedDate = expectedDate;
        this.deliveredDate = deliveredDate;
        this.trackingNumber = trackingNumber;
        this.reference = reference;
    }

    /**
     * Gets the user's email.
     *
     * @return the user's email
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Gets the order's ID.
     *
     * @return the order's ID
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Gets the client's ID.
     *
     * @return the client's ID
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Gets the client's name.
     *
     * @return the client's name
     */
    public String getClientName() {
        return clientName;
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
    public String getPurchaseDate() {
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
    public String getExpectedDate() {
        return expectedDate;
    }

    /**
     * Gets the delivered date.
     *
     * @return the delivered date
     */
    public String getDeliveredDate() {
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
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderModel that = (OrderModel) o;

        return Objects.equals(userEmail, that.userEmail) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(clientId, that.clientId) &&
                Objects.equals(clientName, that.clientName) &&
                Objects.equals(item, that.item) &&
                Objects.equals(shipped, that.shipped) &&
                Objects.equals(purchaseDate, that.purchaseDate) &&
                Objects.equals(shippingService, that.shippingService) &&
                Objects.equals(expectedDate, that.expectedDate) &&
                Objects.equals(deliveredDate, that.deliveredDate) &&
                Objects.equals(trackingNumber, that.trackingNumber) &&
                Objects.equals(reference, that.reference);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(userEmail, orderId, clientId, clientName, item, shipped, purchaseDate, shippingService,
                expectedDate, deliveredDate, trackingNumber, reference);
    }

    /**
     * Creates a new Builder instance.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for constructing OrderModel instances.
     */
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
         * Sets the user's email.
         *
         * @param email the user's email
         * @return the Builder instance
         */
        public Builder withUserEmail(String email) {
            this.userEmail = email;
            return this;
        }

        /**
         * Sets the order's ID.
         *
         * @param id the order's ID
         * @return the Builder instance
         */
        public Builder withOrderId(String id) {
            this.orderId = id;
            return this;
        }

        /**
         * Sets the client's ID.
         *
         * @param id the client's ID
         * @return the Builder instance
         */
        public Builder withClientId(String id) {
            this.clientId = id;
            return this;
        }

        /**
         * Sets the client's name.
         *
         * @param name the client's name
         * @return the Builder instance
         */
        public Builder withClientName(String name) {
            this.clientName = name;
            return this;
        }

        /**
         * Sets the item.
         *
         * @param orderItem the item
         * @return the Builder instance
         */
        public Builder withItem(String orderItem) {
            this.item = orderItem;
            return this;
        }

        /**
         * Sets the shipped status.
         *
         * @param itemShipped the shipped status
         * @return the Builder instance
         */
        public Builder withShipped(Boolean itemShipped) {
            this.shipped = itemShipped;
            return this;
        }

        /**
         * Sets the purchase date.
         *
         * @param date the purchase date
         * @return the Builder instance
         */
        public Builder withPurchaseDate(String date) {
            this.purchaseDate = date;
            return this;
        }

        /**
         * Sets the shipping service.
         *
         * @param service the shipping service
         * @return the Builder instance
         */
        public Builder withShippingService(String service) {
            this.shippingService = service;
            return this;
        }

        /**
         * Sets the expected delivery date.
         *
         * @param date the expected delivery date
         * @return the Builder instance
         */
        public Builder withExpectedDate(String date) {
            this.expectedDate = date;
            return this;
        }

        /**
         * Sets the delivered date.
         *
         * @param date the delivered date
         * @return the Builder instance
         */
        public Builder withDeliveredDate(String date) {
            this.deliveredDate = date;
            return this;
        }

        /**
         * Sets the tracking number.
         *
         * @param tracking the tracking number
         * @return the Builder instance
         */
        public Builder withTrackingNumber(String tracking) {
            this.trackingNumber = tracking;
            return this;
        }

        /**
         * Sets the reference.
         *
         * @param adReference the reference
         * @return the Builder instance
         */
        public Builder withReference(String adReference) {
            this.reference = adReference;
            return this;
        }

        /**
         * Builds a new OrderModel instance with the specified values.
         *
         * @return a new OrderModel instance
         */
        public OrderModel build() {
            return new OrderModel(userEmail, orderId, clientId, clientName,
                    item, shipped, purchaseDate, shippingService,
                    expectedDate, deliveredDate, trackingNumber, reference);
        }
    }
}
