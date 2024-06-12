package com.nashss.se.clientkeeper.activity.results;

import com.nashss.se.clientkeeper.models.OrderModel;

/**
 * Represents the result of editing an order.
 */
public class EditOrderResult {
    private final OrderModel order;

    /**
     * Private constructor to create a new instance of EditOrderResult.
     *
     * @param order the order that was edited
     */
    private EditOrderResult(OrderModel order) {
        this.order = order;
    }

    public OrderModel getOrder() {
        return order;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private OrderModel order;

        public Builder withOrder(OrderModel editedOrder) {
            this.order = editedOrder;
            return this;
        }

        public EditOrderResult build() {
            return new EditOrderResult(order);
        }
    }
}
