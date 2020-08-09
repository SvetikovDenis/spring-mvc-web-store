package com.svetikov.ecommerceshop.service.order;

import com.svetikov.ecommerceshop.model.order.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    OrderDetail getById(Long id);

    OrderDetail getByUserId(Long id);

    List<OrderDetail> getAllByUserId(Long id);

    OrderDetail save(OrderDetail orderDetail);

    void delete(OrderDetail orderDetail);

    void deleteById(Long id);


}
