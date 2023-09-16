package com.abenezersefinew.orderservice.services;

import com.abenezersefinew.orderservice.entities.Order;
import com.abenezersefinew.orderservice.models.OrderRequestModel;
import com.abenezersefinew.orderservice.repositories.OrderRepository;
import com.abenezersefinew.orderservice.services.interfaces.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public Long placeOrder(OrderRequestModel orderRequestModel) {
        log.info("Placing order request...");
        Order order = Order.builder()
                .amount(orderRequestModel.getTotalAmount())
                .productId(orderRequestModel.getProductId())
                .quantity(orderRequestModel.getQuantity())
                .orderDate(Instant.now())
                .orderStatus("CREATED")
                .build();
        Order newOrder = orderRepository.save(order);
        log.info("Order placed.");
        return order.getId();
    }
}
