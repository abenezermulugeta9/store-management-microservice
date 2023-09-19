package com.abenezersefinew.orderservice.models;

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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductDetails {
        private Long productId;
        private String productName;
        private Long price;
        private Long quantity;
    }

}
