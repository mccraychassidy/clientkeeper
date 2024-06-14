package com.nashss.se.clientkeeper.activity.results;

import com.nashss.se.clientkeeper.models.OrderModel;

/**
 * Represents the result of editing an order.
 */
public class EditOrderResult {
    private final OrderModel order;

    private EditOrderResult(OrderModel order) {
        this.order = order;
    }

    public OrderModel getOrder() {
        return order;
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
         * Sets the edited order.
         *
         * @param editedOrder the edited order
         * @return the Builder instance
         */
        public Builder withOrder(OrderModel editedOrder) {
            this.order = editedOrder;
            return this;
        }

        /**
         * Builds a new EditOrderResult instance with the specified edited order.
         *
         * @return a new EditOrderResult instance
         */
        public EditOrderResult build() {
            return new EditOrderResult(order);
        }
    }
}
