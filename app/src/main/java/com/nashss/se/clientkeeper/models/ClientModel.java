package com.nashss.se.clientkeeper.models;

import java.util.Objects;

/**
 * Represents a client.
 */
public class ClientModel {
    private final String userEmail;
    private final String clientId;
    private final String clientName;
    private final String clientEmail;
    private final String clientPhone;
    private final String clientAddress;
    private final String clientMemberSince;

    /**
     * Private constructor for creating a ClientModel instance.
     *
     * @param userEmail the user's email
     * @param clientId the client's ID
     * @param clientName the client's name
     * @param clientEmail the client's email
     * @param clientPhone the client's phone number
     * @param clientAddress the client's address
     * @param clientMemberSince the date the client became a member
     */
    private ClientModel(String userEmail, String clientId, String clientName,
                        String clientEmail, String clientPhone, String clientAddress,
                        String clientMemberSince) {
        this.userEmail = userEmail;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.clientPhone = clientPhone;
        this.clientAddress = clientAddress;
        this.clientMemberSince = clientMemberSince;
    }

    /**
     * Gets the user's email.
     *
     * @return the user's email
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Gets the client's ID.
     *
     * @return the client's ID
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Gets the client's name.
     *
     * @return the client's name
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Gets the client's email.
     *
     * @return the client's email
     */
    public String getClientEmail() {
        return clientEmail;
    }

    /**
     * Gets the client's phone number.
     *
     * @return the client's phone number
     */
    public String getClientPhone() {
        return clientPhone;
    }

    /**
     * Gets the client's address.
     *
     * @return the client's address
     */
    public String getClientAddress() {
        return clientAddress;
    }

    /**
     * Gets the date the client became a member.
     *
     * @return the date the client became a member
     */
    public String getClientMemberSince() {
        return clientMemberSince;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientModel that = (ClientModel) o;

        return Objects.equals(userEmail, that.userEmail) &&
                Objects.equals(clientId, that.clientId) &&
                Objects.equals(clientName, that.clientName) &&
                Objects.equals(clientEmail, that.clientEmail) &&
                Objects.equals(clientPhone, that.clientPhone) &&
                Objects.equals(clientAddress, that.clientAddress) &&
                Objects.equals(clientMemberSince, that.clientMemberSince);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(userEmail, clientId, clientName, clientEmail, clientPhone,
                clientAddress, clientMemberSince);
    }

    /**
     * Creates a new Builder instance.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for constructing ClientModel instances.
     */
    public static class Builder {
        private String userEmail;
        private String clientId;
        private String clientName;
        private String clientEmail;
        private String clientPhone;
        private String clientAddress;
        private String clientMemberSince;

        /**
         * Sets the user's email.
         *
         * @param email the user's email
         * @return the Builder instance
         */
        public Builder withUserEmail(String email) {
            this.userEmail = email;
            return this;
        }

        /**
         * Sets the client's ID.
         *
         * @param id the client's ID
         * @return the Builder instance
         */
        public Builder withClientId(String id) {
            this.clientId = id;
            return this;
        }

        /**
         * Sets the client's name.
         *
         * @param name the client's name
         * @return the Builder instance
         */
        public Builder withClientName(String name) {
            this.clientName = name;
            return this;
        }

        /**
         * Sets the client's email.
         *
         * @param email the client's email
         * @return the Builder instance
         */
        public Builder withClientEmail(String email) {
            this.clientEmail = email;
            return this;
        }

        /**
         * Sets the client's phone number.
         *
         * @param phone the client's phone number
         * @return the Builder instance
         */
        public Builder withClientPhone(String phone) {
            this.clientPhone = phone;
            return this;
        }

        /**
         * Sets the client's address.
         *
         * @param address the client's address
         * @return the Builder instance
         */
        public Builder withClientAddress(String address) {
            this.clientAddress = address;
            return this;
        }

        /**
         * Sets the date the client became a member.
         *
         * @param memberSince the date the client became a member
         * @return the Builder instance
         */
        public Builder withClientMemberSince(String memberSince) {
            this.clientMemberSince = memberSince;
            return this;
        }

        /**
         * Builds a new ClientModel instance with the specified values.
         *
         * @return a new ClientModel instance
         */
        public ClientModel build() {
            return new ClientModel(userEmail, clientId, clientName, clientEmail, clientPhone,
                    clientAddress, clientMemberSince);
        }
    }
}
