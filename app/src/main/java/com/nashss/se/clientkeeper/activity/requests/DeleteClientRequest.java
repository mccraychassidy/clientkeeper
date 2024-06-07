package com.nashss.se.clientkeeper.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents a request to delete a client.
 */
@JsonDeserialize(builder = DeleteClientRequest.Builder.class)
public class DeleteClientRequest {
    private final String userEmail;
    private final String clientId;

    /**
     * Private constructor to create a new instance of DeleteClientRequest.
     *
     * @param builder the Builder object containing the values to initialize the fields
     */
    private DeleteClientRequest(Builder builder) {
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

    /**
     * Returns a string representation of the DeleteClientRequest object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "DeleteClientRequest{" +
                "userEmail='" + userEmail + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }

    /**
     * Builder class for DeleteClientRequest instances.
     */
    @JsonPOJOBuilder
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
         * Builds a new DeleteClientRequest instance with the specified values.
         *
         * @return a new DeleteClientRequest instance
         */
        public DeleteClientRequest build() {
            return new DeleteClientRequest(this);
        }
    }
}
