package com.nashss.se.clientkeeper.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents a request to create a new client.
 */
@JsonDeserialize(builder = CreateClientRequest.Builder.class)
public class CreateClientRequest {
    private final String userEmail;
    private final String clientId;
    private final String clientName;
    private final String clientEmail;
    private final String clientPhone;
    private final String clientAddress;
    private final String clientMemberSince;

    /**
     * Private constructor to create a new instance of CreateClientRequest.
     *
     * @param builder the Builder object containing the values to initialize the fields
     */
    private CreateClientRequest(Builder builder) {
        this.userEmail = builder.userEmail;
        this.clientId = builder.clientId;
        this.clientName = builder.clientName;
        this.clientEmail = builder.clientEmail;
        this.clientPhone = builder.clientPhone;
        this.clientAddress = builder.clientAddress;
        this.clientMemberSince = builder.clientMemberSince;
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
     * Gets the client name.
     *
     * @return the client name
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Gets the client email.
     *
     * @return the client email
     */
    public String getClientEmail() {
        return clientEmail;
    }

    /**
     * Gets the client phone number.
     *
     * @return the client phone number
     */
    public String getClientPhone() {
        return clientPhone;
    }

    /**
     * Gets the client address.
     *
     * @return the client address
     */
    public String getClientAddress() {
        return clientAddress;
    }

    /**
     * Gets the date when the client became a member.
     *
     * @return the date the client became a member
     */
    public String getClientMemberSince() {
        return clientMemberSince;
    }

    /**
     * Returns a string representation of the CreateClientRequest object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "CreateClientRequest{" +
                "userEmail='" + userEmail + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientName='" + clientName + '\'' +
                ", clientEmail='" + clientEmail + '\'' +
                ", clientPhone='" + clientPhone + '\'' +
                ", clientAddress='" + clientAddress + '\'' +
                ", clientMemberSince='" + clientMemberSince + '\'' +
                '}';
    }

    /**
     * Builder class for CreateClientRequest instances.
     */
    @JsonPOJOBuilder
    public static class Builder {
        private String userEmail;
        private String clientId;
        private String clientName;
        private String clientEmail;
        private String clientPhone;
        private String clientAddress;
        private String clientMemberSince;

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
         * Sets the client name.
         *
         * @param name the client name
         * @return the Builder instance
         */
        public Builder withClientName(String name) {
            this.clientName = name;
            return this;
        }

        /**
         * Sets the client email.
         *
         * @param email the client email
         * @return the Builder instance
         */
        public Builder withClientEmail(String email) {
            this.clientEmail = email;
            return this;
        }

        /**
         * Sets the client phone number.
         *
         * @param phone the client phone number
         * @return the Builder instance
         */
        public Builder withClientPhone(String phone) {
            this.clientPhone = phone;
            return this;
        }

        /**
         * Sets the client address.
         *
         * @param address the client address
         * @return the Builder instance
         */
        public Builder withClientAddress(String address) {
            this.clientAddress = address;
            return this;
        }

        /**
         * Sets the date when the client became a member.
         *
         * @param memberSince the date the client became a member
         * @return the Builder instance
         */
        public Builder withClientMemberSince(String memberSince) {
            this.clientMemberSince = memberSince;
            return this;
        }

        /**
         * Builds a new CreateClientRequest instance with the specified values.
         *
         * @return a new CreateClientRequest instance
         */
        public CreateClientRequest build() {
            return new CreateClientRequest(this);
        }
    }
}
