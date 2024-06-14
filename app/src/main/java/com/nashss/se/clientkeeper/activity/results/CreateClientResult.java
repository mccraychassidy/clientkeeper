package com.nashss.se.clientkeeper.activity.results;

import com.nashss.se.clientkeeper.models.ClientModel;

/**
 * Represents the result of creating a new client.
 */
public class CreateClientResult {
    private final ClientModel client;

    private CreateClientResult(ClientModel client) {
        this.client = client;
    }

    public ClientModel getClient() {
        return client;
    }

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
