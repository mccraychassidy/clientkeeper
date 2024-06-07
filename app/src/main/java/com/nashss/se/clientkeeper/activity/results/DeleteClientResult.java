package com.nashss.se.clientkeeper.activity.results;

/**
 * Represents the result of deleting a client.
 */
public class DeleteClientResult {
    private final boolean success;

    /**
     * Private constructor to create a new instance of DeleteClientResult.
     *
     * @param success the result of the deletion
     */
    private DeleteClientResult(boolean success) {
        this.success = success;
    }

    /**
     * Indicates whether the deletion was successful.
     *
     * @return true if the deletion was successful, false otherwise
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Returns a string representation of the DeleteClientResult object.
     *
     * @return a string representation of the object
     */
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

    /**
     * Builder class for constructing DeleteClientResult instances.
     */
    public static class Builder {
        private boolean success;

        /**
         * Sets the result of the deletion.
         *
         * @param success the result of the deletion
         * @return the Builder instance
         */
        public Builder withSuccess(boolean success) {
            this.success = success;
            return this;
        }

        /**
         * Builds a new DeleteClientResult instance with the specified result.
         *
         * @return a new DeleteClientResult instance
         */
        public DeleteClientResult build() {
            return new DeleteClientResult(success);
        }
    }
}
