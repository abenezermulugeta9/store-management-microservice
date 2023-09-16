package com.abenezersefinew.productservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseModel {
    private Long productId;
    private String productName;
    private Long price;
    private Long quantity;
}
