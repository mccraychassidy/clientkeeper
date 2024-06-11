package com.nashss.se.clientkeeper.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents a request to get orders without a delivered date.
 */
@JsonDeserialize(builder = GetUndeliveredOrdersRequest.Builder.class)
public class GetUndeliveredOrdersRequest {
    private final String userEmail;

    private GetUndeliveredOrdersRequest(Builder builder) {
        this.userEmail = builder.userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userEmail;

        public Builder withUserEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public GetUndeliveredOrdersRequest build() {
            return new GetUndeliveredOrdersRequest(this);
        }
    }
}
