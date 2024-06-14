package com.nashss.se.clientkeeper.activity.results;

import com.nashss.se.clientkeeper.models.OrderModel;

import java.util.List;

/**
 * Represents the result of getting orders with a delivered date.
 */
public class GetDeliveredOrdersResult {
    private final List<OrderModel> orders;

    private GetDeliveredOrdersResult(Builder builder) {
        this.orders = builder.orders;
    }

    public List<OrderModel> getOrders() {
        return orders;
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
        private List<OrderModel> orders;

        /**
         * Sets the list of orders with a delivered date.
         *
         * @param orderModels the list of orders
         * @return the Builder instance
         */
        public Builder withOrders(List<OrderModel> orderModels) {
            this.orders = orderModels;
            return this;
        }

        /**
         * Builds a new GetDeliveredOrdersResult instance with the specified list of orders.
         *
         * @return a new GetDeliveredOrdersResult instance
         */
        public GetDeliveredOrdersResult build() {
            return new GetDeliveredOrdersResult(this);
        }
    }
}
