package com.abenezersefinew.productservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductQuantityExceedException extends RuntimeException {
    private String exceptionCode;
    public ProductQuantityExceedException(String exceptionMessage, String exceptionCode) {
        super(exceptionMessage);
        this.exceptionCode = exceptionCode;
    }
}
