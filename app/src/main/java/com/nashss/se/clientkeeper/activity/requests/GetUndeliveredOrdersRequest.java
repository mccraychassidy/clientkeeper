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
         * Builds a new GetUndeliveredOrdersRequest instance with the specified user email.
         *
         * @return a new GetUndeliveredOrdersRequest instance
         */
        public GetUndeliveredOrdersRequest build() {
            return new GetUndeliveredOrdersRequest(this);
        }
    }
}
