package com.nashss.se.clientkeeper.activity.results;

/**
 * Represents the result of deleting a client.
 */
public class DeleteClientResult {
    private final boolean success;

    private DeleteClientResult(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "DeleteClientResult{" +
                "success=" + success +
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
        private boolean success;

        /**
         * Sets the success status of the delete operation.
         *
         * @param isSuccess the success status
         * @return the Builder instance
         */
        public Builder withSuccess(boolean isSuccess) {
            this.success = isSuccess;
            return this;
        }

        /**
         * Builds a new DeleteClientResult instance with the specified success status.
         *
         * @return a new DeleteClientResult instance
         */
        public DeleteClientResult build() {
            return new DeleteClientResult(success);
        }
    }
}
