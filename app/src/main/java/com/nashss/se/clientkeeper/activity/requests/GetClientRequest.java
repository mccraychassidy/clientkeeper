package com.nashss.se.clientkeeper.activity.requests;

/**
 * Represents a request to get a client.
 */
public class GetClientRequest {
    private final String userEmail;
    private final String clientId;

    /**
     * Private constructor to create a new instance of GetClientRequest.
     *
     * @param builder the Builder object containing the values to initialize the fields
     */
    private GetClientRequest(Builder builder) {
        this.userEmail = builder.userEmail;
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

    /**
     * Gets the user email.
     *
     * @return the user email
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Gets the client ID.
     *
     * @return the client ID
     */
    public String getClientId() {
        return clientId;
    }

    public static class Builder {
        private String userEmail;
        private String clientId;

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
         * Builds a new GetClientRequest instance with the specified values.
         *
         * @return a new GetClientRequest instance
         */
        public GetClientRequest build() {
            return new GetClientRequest(this);
        }
    }
}
