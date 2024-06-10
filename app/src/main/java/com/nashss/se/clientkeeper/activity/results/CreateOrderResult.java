package com.nashss.se.clientkeeper.activity.results;

import com.nashss.se.clientkeeper.models.OrderModel;

/**
 * Represents the result of creating a new order.
 */
public class CreateOrderResult {
    private final OrderModel order;

    /**
     * Private constructor to create a new instance of CreateOrderResult.
     *
     * @param order the order that was created
     */
    private CreateOrderResult(OrderModel order) {
        this.order = order;
    }

    /**
     * Gets the order that was created.
     *
     * @return the created order
     */
    public OrderModel getOrder() {
        return order;
    }

    /**
     * Returns a string representation of the CreateOrderResult object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "CreateOrderResult{" +
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
     * Builder class for constructing CreateOrderResult instances.
     */
    public static class Builder {
        private OrderModel order;

        /**
         * Sets the order that was created.
         *
         * @param createdOrder the created order
         * @return the Builder instance
         */
        public Builder withOrder(OrderModel createdOrder) {
            this.order = createdOrder;
            return this;
        }

        /**
         * Builds a new CreateOrderResult instance with the specified order.
         *
         * @return a new CreateOrderResult instance
         */
        public CreateOrderResult build() {
            return new CreateOrderResult(order);
        }
    }
}
