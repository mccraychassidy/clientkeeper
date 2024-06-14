package com.nashss.se.clientkeeper.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents a request to delete an order.
 */
@JsonDeserialize(builder = DeleteOrderRequest.Builder.class)
public class DeleteOrderRequest {
    private final String userEmail;
    private final String orderId;

    /**
     * Private constructor to create a new instance of DeleteOrderRequest.
     *
     * @param builder the Builder object containing the values to initialize the fields
     */
    private DeleteOrderRequest(Builder builder) {
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

    @Override
    public String toString() {
        return "DeleteOrderRequest{" +
                "userEmail='" + userEmail + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }

    @JsonPOJOBuilder
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
         * Builds a new DeleteOrderRequest instance with the specified values.
         *
         * @return a new DeleteOrderRequest instance
         */
        public DeleteOrderRequest build() {
            return new DeleteOrderRequest(this);
        }
    }
}
