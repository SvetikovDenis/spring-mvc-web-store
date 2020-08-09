package com.svetikov.ecommerceshop.service.order.impl;

import com.svetikov.ecommerceshop.exception.EntityNotFoundException;
import com.svetikov.ecommerceshop.model.order.OrderDetail;
import com.svetikov.ecommerceshop.repository.order.OrderDetailRepository;
import com.svetikov.ecommerceshop.service.order.OrderDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl  implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    private final Logger log = LoggerFactory.getLogger(OrderDetailServiceImpl.class);

    @Override
    public OrderDetail getById(Long id) {
        OrderDetail orderDetail = orderDetailRepository.getById(id);
        if (orderDetail == null) {
            log.warn("In getById - order detail with id : {} was not found", id);
            throw new EntityNotFoundException(OrderDetail.class, "id", id.toString());
        }
        log.info("In getById - order detail with id : {} was found ",id);
        return orderDetail;
    }

    @Override
    public OrderDetail getByUserId(Long id) {
        OrderDetail orderDetail = orderDetailRepository.getOrderDetailByUserId(id);
        if (orderDetail == null) {
            log.warn("In getByUserId - order detail with user id : {} was not found", id);
            throw new EntityNotFoundException(OrderDetail.class, "user id", id.toString());
        }
        log.info("In getByUserId - order detail with user id : {} was found ",id);
        return orderDetail;
    }

    @Override
    public List<OrderDetail> getAllByUserId(Long id) {
        List<OrderDetail> orderDetails =orderDetailRepository.getAllByUserId(id);
        if (orderDetails.isEmpty()) {
            log.info("In getAllByUserId - no orders was found for user with id : {}",id);
        }
        log.info("In getAllByUserId - {} orders was found for user with id : {}" ,orderDetails.size(),id);
        return orderDetails;
    }

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        OrderDetail newOrderDetail = orderDetailRepository.save(orderDetail);
        log.info("In save - order with id : {} for user with : {} was saved",newOrderDetail.getId(),orderDetail.getUser().getId());
        return newOrderDetail;
    }

    @Override
    public void delete(OrderDetail orderDetail) {
        orderDetailRepository.delete(orderDetail);
        log.info("In delete - order detail with id : {} was deleted",orderDetail.getId());
    }

    @Override
    public void deleteById(Long id) {
        orderDetailRepository.deleteById(id);
        log.info("In deleteById - order detail with id : {} was deleted",id);
    }
}
