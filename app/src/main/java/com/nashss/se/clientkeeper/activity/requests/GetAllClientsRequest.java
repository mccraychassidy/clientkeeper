package com.nashss.se.clientkeeper.activity.requests;

/**
 * Represents a request to get all clients.
 */
public class GetAllClientsRequest {
    private final String userEmail;

    /**
     * Private constructor to create a new instance of GetAllClientsRequest.
     *
     * @param builder the Builder object containing the values to initialize the fields
     */
    private GetAllClientsRequest(Builder builder) {
        this.userEmail = builder.userEmail;
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
         * Builds a new GetAllClientsRequest instance.
         *
         * @return a new GetClientRequest instance
         */
        public GetAllClientsRequest build() {
            return new GetAllClientsRequest(this);
        }
    }
}
