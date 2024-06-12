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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<OrderModel> orders;

        public Builder withOrders(List<OrderModel> orders) {
            this.orders = orders;
            return this;
        }

        public GetDeliveredOrdersResult build() {
            return new GetDeliveredOrdersResult(this);
        }
    }
}
