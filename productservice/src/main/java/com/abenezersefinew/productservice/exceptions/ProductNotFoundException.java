package com.abenezersefinew.productservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductNotFoundException extends RuntimeException{
    private String exceptionCode;
    public ProductNotFoundException(String exceptionMessage, String exceptionCode){
        super(exceptionMessage);
        this.exceptionCode = exceptionCode;
    }
}
