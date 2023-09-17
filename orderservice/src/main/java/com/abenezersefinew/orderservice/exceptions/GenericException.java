package com.abenezersefinew.orderservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericException extends RuntimeException{
    private String exceptionCode;
    private Integer httpStatus;

    public GenericException(String message, String exceptionCode, Integer httpStatus) {
        super(message);
        this.exceptionCode = exceptionCode;
        this.httpStatus = httpStatus;
    }
}
