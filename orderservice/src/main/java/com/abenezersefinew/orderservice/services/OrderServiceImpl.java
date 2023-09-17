package com.abenezersefinew.orderservice.services;

import com.abenezersefinew.orderservice.entities.Order;
import com.abenezersefinew.orderservice.external.clients.ProductService;
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
    private final ProductService productService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public Long placeOrder(OrderRequestModel orderRequestModel) {
        log.info("Placing order request...");

        /** Create order with "CREATED" order status. */
        Order order = Order.builder()
                .amount(orderRequestModel.getTotalAmount())
                .productId(orderRequestModel.getProductId())
                .quantity(orderRequestModel.getQuantity())
                .orderDate(Instant.now())
                .orderStatus("CREATED")
                .build();
        Order newOrder = orderRepository.save(order);
        log.info("Order placed.");

        /** @external - Reduce product quantity in the Product Service. */
        log.info("Calling reduce product resource in product service...");
        productService.reduceQuantity(orderRequestModel.getProductId(), orderRequestModel.getQuantity());
        log.info("Call resulted in successful operation.");

        /** Process payment. */
        return newOrder.getId();
    }
}
