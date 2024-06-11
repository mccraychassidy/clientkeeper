package com.nashss.se.clientkeeper.activity.results;

import com.nashss.se.clientkeeper.models.OrderModel;
import java.util.List;

/**
 * Represents the result of getting orders without a delivered date.
 */
public class GetUndeliveredOrdersResult {
    private final List<OrderModel> orders;

    private GetUndeliveredOrdersResult(Builder builder) {
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

        public GetUndeliveredOrdersResult build() {
            return new GetUndeliveredOrdersResult(this);
        }
    }
}
