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

    public static Builder builder() {
        return new Builder();
    }

    public List<OrderModel> getOrders() {
        return orders;
    }

    public static class Builder {
        private List<OrderModel> orders;

        public Builder withOrders(List<OrderModel> orders) {
            this.orders = orders;
            return this;
        }

        public GetOrdersByClientIdResult build() {
            return new GetOrdersByClientIdResult(this);
        }
    }
}
