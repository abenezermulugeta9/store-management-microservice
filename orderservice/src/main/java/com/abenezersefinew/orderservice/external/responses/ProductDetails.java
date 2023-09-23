package com.abenezersefinew.orderservice.external.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetails {
    private Long productId;
    private String productName;
    private Long price;
    private Long quantity;
}
