package com.abenezersefinew.productservice.services.interfaces;

import com.abenezersefinew.productservice.models.ProductRequestModel;
import com.abenezersefinew.productservice.models.ProductResponseModel;

public interface ProductService {
    Long create(ProductRequestModel productRequestModel);
    ProductResponseModel getById(Long id);
    void reduceQuantity(Long productId, Long quantity);
}
