package com.nashss.se.clientkeeper.activity.results;

import com.nashss.se.clientkeeper.models.ClientModel;

/**
 * Represents the result of creating a new client.
 */
public class CreateClientResult {
    private final ClientModel client;

    /**
     * Private constructor to create a new instance of CreateClientResult.
     *
     * @param client the client that was created
     */
    private CreateClientResult(ClientModel client) {
        this.client = client;
    }

    /**
     * Gets the client that was created.
     *
     * @return the created client
     */
    public ClientModel getClient() {
        return client;
    }

    /**
     * Returns a string representation of the CreateClientResult object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "CreateClientResult{" +
                "client=" + client +
                '}';
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
     * Builder class for constructing CreateClientResult instances.
     */
    public static class Builder {
        private ClientModel client;

        /**
         * Sets the client that was created.
         *
         * @param createdClient the created client
         * @return the Builder instance
         */
        public Builder withClient(ClientModel createdClient) {
            this.client = createdClient;
            return this;
        }

        /**
         * Builds a new CreateClientResult instance with the specified client.
         *
         * @return a new CreateClientResult instance
         */
        public CreateClientResult build() {
            return new CreateClientResult(client);
        }
    }
}
