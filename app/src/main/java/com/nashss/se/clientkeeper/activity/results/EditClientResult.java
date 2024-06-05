package com.nashss.se.clientkeeper.activity.results;

import com.nashss.se.clientkeeper.models.ClientModel;

/**
 * Represents the result of editing a client.
 */
public class EditClientResult {
    private final ClientModel client;

    /**
     * Private constructor to create a new instance of EditClientResult.
     *
     * @param client the client that was edited
     */
    private EditClientResult(ClientModel client) {
        this.client = client;
    }

    /**
     * Gets the client that was edited.
     *
     * @return the edited client
     */
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

    /**
     * Builder class for constructing EditClientResult instances.
     */
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
