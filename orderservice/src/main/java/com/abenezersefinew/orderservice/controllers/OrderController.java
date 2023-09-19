package com.abenezersefinew.orderservice.controllers;

import com.abenezersefinew.orderservice.models.OrderRequestModel;
import com.abenezersefinew.orderservice.services.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place-order")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequestModel orderRequestModel) {
        Long orderId = orderService.placeOrder(orderRequestModel);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }


}
