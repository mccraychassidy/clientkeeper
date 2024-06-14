package com.nashss.se.clientkeeper.activity.results;

import com.nashss.se.clientkeeper.models.ClientModel;

import java.util.List;

/**
 * Represents the result of getting all clients.
 */
public class GetAllClientsResult {
    private final List<ClientModel> clients;

    private GetAllClientsResult(List<ClientModel> clients) {
        this.clients = clients;
    }

    public List<ClientModel> getClients() {
        return clients;
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
        private List<ClientModel> clients;

        /**
         * Sets the clients that were retrieved.
         *
         * @param retrievedClients the retrieved clients
         * @return the Builder instance
         */
        public Builder withClients(List<ClientModel> retrievedClients) {
            this.clients = retrievedClients;
            return this;
        }

        /**
         * Builds a new GetAllClientsResult instance with the specified clients.
         *
         * @return a new GetAllClientsResult instance
         */
        public GetAllClientsResult build() {
            return new GetAllClientsResult(this.clients);
        }
    }
}
