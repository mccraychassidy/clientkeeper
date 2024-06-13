package com.nashss.se.clientkeeper.activity.results;

/**
 * Represents the result of deleting an order.
 */
public class DeleteOrderResult {
    private final boolean success;

    /**
     * Private constructor to create a new instance of DeleteOrderResult.
     *
     * @param success the success status of the delete operation
     */
    private DeleteOrderResult(boolean success) {
        this.success = success;
    }

    /**
     * Gets the success status of the delete operation.
     *
     * @return the success status
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Returns a string representation of the DeleteOrderResult object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "DeleteOrderResult{" +
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
     * Builder class for constructing DeleteOrderResult instances.
     */
    public static class Builder {
        private boolean success;

        /**
         * Sets the success status of the delete operation.
         *
         * @param success the success status
         * @return the Builder instance
         */
        public Builder withSuccess(boolean success) {
            this.success = success;
            return this;
        }

        /**
         * Builds a new DeleteOrderResult instance with the specified success status.
         *
         * @return a new DeleteOrderResult instance
         */
        public DeleteOrderResult build() {
            return new DeleteOrderResult(success);
        }
    }
}
