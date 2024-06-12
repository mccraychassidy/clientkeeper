package com.nashss.se.clientkeeper.activity.results;

import com.nashss.se.clientkeeper.models.OrderModel;

/**
 * Represents the result of getting an order.
 */
public class GetOrderResult {
    private final OrderModel order;

    /**
     * Private constructor to create a new instance of GetOrderResult.
     *
     * @param order the order that was retrieved
     */
    private GetOrderResult(OrderModel order) {
        this.order = order;
    }

    /**
     * Gets the order that was retrieved.
     *
     * @return the retrieved order
     */
    public OrderModel getOrder() {
        return order;
    }

    /**
     * Returns a string representation of the GetOrderResult object.
     *
     * @return a string representation of the object
     */
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

    /**
     * Builder class for constructing GetOrderResult instances.
     */
    public static class Builder {
        private OrderModel order;

        /**
         * Sets the order that was retrieved.
         *
         * @param retrievedOrder the retrieved order
         * @return the Builder instance
         */
        public Builder withOrder(OrderModel retrievedOrder) {
            this.order = retrievedOrder;
            return this;
        }

        /**
         * Builds a new GetOrderResult instance with the specified order.
         *
         * @return a new GetOrderResult instance
         */
        public GetOrderResult build() {
            return new GetOrderResult(order);
        }
    }
}
