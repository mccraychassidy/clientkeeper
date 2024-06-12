package com.nashss.se.clientkeeper.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents a request to get orders with a delivered date.
 */
@JsonDeserialize(builder = GetDeliveredOrdersRequest.Builder.class)
public class GetDeliveredOrdersRequest {
    private final String userEmail;

    private GetDeliveredOrdersRequest(Builder builder) {
        this.userEmail = builder.userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for GetDeliveredOrdersRequest instances.
     */
    @JsonPOJOBuilder
    public static class Builder {
        private String userEmail;

        public Builder withUserEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public GetDeliveredOrdersRequest build() {
            return new GetDeliveredOrdersRequest(this);
        }
    }
}
