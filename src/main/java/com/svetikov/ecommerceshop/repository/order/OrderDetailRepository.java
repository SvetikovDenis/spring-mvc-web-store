package com.svetikov.ecommerceshop.repository.order;

import com.svetikov.ecommerceshop.model.order.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    OrderDetail getById(Long id);

    OrderDetail getOrderDetailByUserId(Long id);

    List<OrderDetail> getAllByUserId(Long id);


}
