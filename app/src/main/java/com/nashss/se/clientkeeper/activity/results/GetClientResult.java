package com.nashss.se.clientkeeper.activity.results;

import com.nashss.se.clientkeeper.models.ClientModel;

/**
 * Represents the result of getting a client.
 */
public class GetClientResult {
    private final ClientModel client;

    private GetClientResult(ClientModel client) {
        this.client = client;
    }

    public ClientModel getClient() {
        return client;
    }

    @Override
    public String toString() {
        return "GetClientResult{" +
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
         * Sets the client that was retrieved.
         *
         * @param retrievedClient the retrieved client
         * @return the Builder instance
         */
        public Builder withClient(ClientModel retrievedClient) {
            this.client = retrievedClient;
            return this;
        }

        /**
         * Builds a new GetClientResult instance with the specified client.
         *
         * @return a new GetClientResult instance
         */
        public GetClientResult build() {
            return new GetClientResult(client);
        }
    }
}
