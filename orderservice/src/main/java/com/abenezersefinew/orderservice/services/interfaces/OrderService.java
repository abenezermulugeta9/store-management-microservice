package com.abenezersefinew.orderservice.services.interfaces;

import com.abenezersefinew.orderservice.models.OrderRequestModel;
import com.abenezersefinew.orderservice.models.OrderResponseModel;

public interface OrderService {
    Long placeOrder(OrderRequestModel orderRequestModel);
    OrderResponseModel getOrderDetails(Long orderId);
}
