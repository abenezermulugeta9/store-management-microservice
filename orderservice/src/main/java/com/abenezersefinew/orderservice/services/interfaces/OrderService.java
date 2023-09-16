package com.abenezersefinew.orderservice.services.interfaces;

import com.abenezersefinew.orderservice.models.OrderRequestModel;

public interface OrderService {
    Long placeOrder(OrderRequestModel orderRequestModel);
}
