package com.nashss.se.clientkeeper.activity.requests;

/**
 * Represents a request to get orders by clientId.
 */
public class GetOrdersByClientIdRequest {
    private final String clientId;

    private GetOrdersByClientIdRequest(Builder builder) {
        this.clientId = builder.clientId;
    }

    /**
     * Returns a new Builder instance.
     *
     * @return a new Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public String getClientId() {
        return clientId;
    }

    public static class Builder {
        private String clientId;

        /**
         * Sets the clientId.
         *
         * @param id the clientId
         * @return the Builder instance
         */
        public Builder withClientId(String id) {
            this.clientId = id;
            return this;
        }

        /**
         * Builds a new GetOrdersByClientIdRequest instance with the specified clientId.
         *
         * @return a new GetOrdersByClientIdRequest instance
         */
        public GetOrdersByClientIdRequest build() {
            return new GetOrdersByClientIdRequest(this);
        }
    }
}
