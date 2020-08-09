package com.svetikov.ecommerceshop.service.order.impl;

import com.svetikov.ecommerceshop.exception.EntityNotFoundException;
import com.svetikov.ecommerceshop.model.order.OrderItem;
import com.svetikov.ecommerceshop.repository.order.OrderItemRepository;
import com.svetikov.ecommerceshop.service.order.OrderItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {


    @Autowired
    private OrderItemRepository orderItemRepository;

    private final Logger log = LoggerFactory.getLogger(OrderItemServiceImpl.class);

    @Override
    public OrderItem getById(Long id) {
        OrderItem orderItem = orderItemRepository.getById(id);
        if (orderItem == null) {
            log.warn("In getById - order item with id : {} was not found", id);
            throw new EntityNotFoundException(OrderItem.class, "id", id.toString());
        }
        log.info("In getById - order item with id : {} was found", id);
        return orderItem;
    }


    @Override
    public List<OrderItem> getOrderItemsByOrderDetail(Long id) {
        List<OrderItem> orderItems = orderItemRepository.getAllByOrderDetailId(id);
        if (orderItems.isEmpty()) {
            log.warn("In getOrderItemsByOrderDetail - no order items was found for order detail with id : {}", id);
        }
        log.info("In getOrderItemsByOrderDetail - {} order items was found for order detail with id : {}", orderItems.size(), id);
        return orderItems;
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        OrderItem newOrderItem = orderItemRepository.save(orderItem);
        log.info("In save - order item with id : {} was saved", newOrderItem.getId());
        return newOrderItem;
    }

    @Override
    public void delete(OrderItem orderItem) {
        orderItemRepository.delete(orderItem);
        log.info("In delete - order item with id : {} was deleted", orderItem.getId());
    }

    @Override
    public void deleteById(Long id) {
        orderItemRepository.deleteById(id);
        log.info("In deleteById - order item with id : {} was deleted", id);
    }
}
