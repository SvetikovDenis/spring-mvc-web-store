package com.svetikov.ecommerceshop.service.order;

import com.svetikov.ecommerceshop.model.order.OrderItem;

import java.util.List;

public interface OrderItemService {

    OrderItem getById(Long id);

    List<OrderItem> getOrderItemsByOrderDetail(Long id);

    OrderItem save(OrderItem orderItem);

    void delete(OrderItem orderItem);

    void deleteById(Long id);


}
