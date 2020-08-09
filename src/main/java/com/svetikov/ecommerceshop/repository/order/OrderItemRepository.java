package com.svetikov.ecommerceshop.repository.order;

import com.svetikov.ecommerceshop.model.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

    OrderItem getById(Long id);

    OrderItem getByOrderDetailId(Long id);

    List<OrderItem> getAllByOrderDetailId(Long id);

}
