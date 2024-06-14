package com.nashss.se.clientkeeper.activity.results;

import com.nashss.se.clientkeeper.models.OrderModel;

import java.util.List;

/**
 * Represents the result of getting orders by clientId.
 */
public class GetOrdersByClientIdResult {
    private final List<OrderModel> orders;

    private GetOrdersByClientIdResult(Builder builder) {
        this.orders = builder.orders;
    }

    /**
     * Returns a new Builder instance.
     *
     * @return a new Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public List<OrderModel> getOrders() {
        return orders;
    }

    public static class Builder {
        private List<OrderModel> orders;

        /**
         * Sets the list of orders by clientId.
         *
         * @param orderModels the list of orders
         * @return the Builder instance
         */
        public Builder withOrders(List<OrderModel> orderModels) {
            this.orders = orderModels;
            return this;
        }

        /**
         * Builds a new GetOrdersByClientIdResult instance with the specified list of orders.
         *
         * @return a new GetOrdersByClientIdResult instance
         */
        public GetOrdersByClientIdResult build() {
            return new GetOrdersByClientIdResult(this);
        }
    }
}
