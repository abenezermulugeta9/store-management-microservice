package com.abenezersefinew.orderservice.models;

import com.abenezersefinew.orderservice.external.responses.PaymentDetails;
import com.abenezersefinew.orderservice.external.responses.ProductDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseModel {
    private Long orderId;
    private Instant orderDate;
    private String orderStatus;
    private Long amount;
    private ProductDetails productDetails;
    private PaymentDetails paymentDetails;
}
