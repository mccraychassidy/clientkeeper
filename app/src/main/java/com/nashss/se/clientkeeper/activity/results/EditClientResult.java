package com.nashss.se.clientkeeper.activity.results;

import com.nashss.se.clientkeeper.models.ClientModel;

/**
 * Represents the result of editing a client.
 */
public class EditClientResult {
    private final ClientModel client;

    private EditClientResult(ClientModel client) {
        this.client = client;
    }

    public ClientModel getClient() {
        return client;
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
         * Sets the client that was edited.
         *
         * @param editedClient the edited client
         * @return the Builder instance
         */
        public Builder withClient(ClientModel editedClient) {
            this.client = editedClient;
            return this;
        }

        /**
         * Builds a new EditClientResult instance with the specified client.
         *
         * @return a new EditClientResult instance
         */
        public EditClientResult build() {
            return new EditClientResult(client);
        }
    }
}
