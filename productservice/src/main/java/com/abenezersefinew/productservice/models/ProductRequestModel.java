package com.abenezersefinew.productservice.models;

import lombok.Data;

@Data
public class ProductRequestModel {
    private String name;
    private Long price;
    private Long quantity;
}
