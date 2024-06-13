package com.nashss.se.clientkeeper.activity.requests;

/**
 * Represents a request to get orders by clientId.
 */
public class GetOrdersByClientIdRequest {
    private final String clientId;

    private GetOrdersByClientIdRequest(Builder builder) {
        this.clientId = builder.clientId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getClientId() {
        return clientId;
    }

    public static class Builder {
        private String clientId;

        public Builder withClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public GetOrdersByClientIdRequest build() {
            return new GetOrdersByClientIdRequest(this);
        }
    }
}
