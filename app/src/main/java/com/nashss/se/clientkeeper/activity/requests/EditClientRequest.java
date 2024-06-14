package com.nashss.se.clientkeeper.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents a request to edit a client.
 */
@JsonDeserialize(builder = EditClientRequest.Builder.class)
public class EditClientRequest {
    private final String userEmail;
    private final String clientId;
    private final String clientName;
    private final String clientEmail;
    private final String clientPhone;
    private final String clientAddress;
    private final String clientMemberSince;

    /**
     * Private constructor to create a new instance of EditClientRequest.
     *
     * @param builder the Builder object containing the values to initialize the fields
     */
    private EditClientRequest(Builder builder) {
        this.userEmail = builder.userEmail;
        this.clientId = builder.clientId;
        this.clientName = builder.clientName;
        this.clientEmail = builder.clientEmail;
        this.clientPhone = builder.clientPhone;
        this.clientAddress = builder.clientAddress;
        this.clientMemberSince = builder.clientMemberSince;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public String getClientMemberSince() {
        return clientMemberSince;
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
         * Builds a new EditClientRequest instance with the specified values.
         *
         * @return a new EditClientRequest instance
         */
        public EditClientRequest build() {
            return new EditClientRequest(this);
        }
    }
}
