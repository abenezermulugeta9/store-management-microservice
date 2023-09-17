package com.abenezersefinew.orderservice.exceptions.handlers;

import com.abenezersefinew.orderservice.exceptions.GenericException;
import com.abenezersefinew.orderservice.external.responses.ExceptionResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ExceptionResponseModel> handleGenericExceptions(GenericException exception) {
        return new ResponseEntity<>(new ExceptionResponseModel().builder()
                .exceptionCode(exception.getExceptionCode())
                .exceptionMessage(exception.getMessage())
                .build(), HttpStatus.valueOf(exception.getHttpStatus()));
    }
}
