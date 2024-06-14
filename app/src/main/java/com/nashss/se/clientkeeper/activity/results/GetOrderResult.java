package com.nashss.se.clientkeeper.activity.results;

import com.nashss.se.clientkeeper.models.OrderModel;

/**
 * Represents the result of getting an order.
 */
public class GetOrderResult {
    private final OrderModel order;

    private GetOrderResult(OrderModel order) {
        this.order = order;
    }

    public OrderModel getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "GetOrderResult{" +
                "order=" + order +
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
        private OrderModel order;

        /**
         * Sets the retrieved order.
         *
         * @param retrievedOrder the retrieved order
         * @return the Builder instance
         */
        public Builder withOrder(OrderModel retrievedOrder) {
            this.order = retrievedOrder;
            return this;
        }

        /**
         * Builds a new GetOrderResult instance with the specified retrieved order.
         *
         * @return a new GetOrderResult instance
         */
        public GetOrderResult build() {
            return new GetOrderResult(order);
        }
    }
}
