package com.nashss.se.clientkeeper.activity.results;

/**
 * Represents the result of deleting an order.
 */
public class DeleteOrderResult {
    private final boolean success;

    private DeleteOrderResult(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

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
         * Builds a new DeleteOrderResult instance with the specified success status.
         *
         * @return a new DeleteOrderResult instance
         */
        public DeleteOrderResult build() {
            return new DeleteOrderResult(success);
        }
    }
}
