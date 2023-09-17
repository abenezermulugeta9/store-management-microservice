package com.abenezersefinew.productservice.exceptions;

public class ProductQuantityExceedException extends RuntimeException {
    public ProductQuantityExceedException(String message) {
        super(message);
    }
}
