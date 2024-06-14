package com.nashss.se.clientkeeper.activity.requests;

/**
 * Represents a request to get an order.
 */
public class GetOrderRequest {
    private final String userEmail;
    private final String orderId;

    /**
     * Private constructor to create a new instance of GetOrderRequest.
     *
     * @param builder the Builder object containing the values to initialize the fields
     */
    private GetOrderRequest(Builder builder) {
        this.userEmail = builder.userEmail;
        this.orderId = builder.orderId;
    }

    /**
     * Returns a new Builder instance.
     *
     * @return a new Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getOrderId() {
        return orderId;
    }

    public static class Builder {
        private String userEmail;
        private String orderId;

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
         * Builds a new GetOrderRequest instance.
         *
         * @return a new GetOrderRequest instance
         */
        public GetOrderRequest build() {
            return new GetOrderRequest(this);
        }
    }
}
