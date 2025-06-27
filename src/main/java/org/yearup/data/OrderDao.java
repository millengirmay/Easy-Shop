package org.yearup.data;

import org.yearup.models.Order;
import org.yearup.models.OrderLineItem;

    public interface OrderDao {
        int create(Order order);                // returns new order ID
        void addLineItem(OrderLineItem item);  // adds line item to DB
    }


