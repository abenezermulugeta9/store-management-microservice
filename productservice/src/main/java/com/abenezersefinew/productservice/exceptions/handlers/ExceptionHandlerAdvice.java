package com.abenezersefinew.productservice.exceptions.handlers;

import com.abenezersefinew.productservice.exceptions.ProductNotFoundException;
import com.abenezersefinew.productservice.exceptions.ProductQuantityExceedException;
import com.abenezersefinew.productservice.models.ExceptionResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponseModel> handleProductNotFoundException(ProductNotFoundException exception) {
        return new ResponseEntity<>(new ExceptionResponseModel().builder()
                .exceptionCode(exception.getExceptionCode())
                .exceptionMessage(exception.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProductQuantityExceedException.class)
    public ResponseEntity<ExceptionResponseModel> handleProductQuantityExceedException(ProductQuantityExceedException exception) {
        return new ResponseEntity<>(new ExceptionResponseModel().builder()
                .exceptionCode(exception.getExceptionCode())
                .exceptionMessage(exception.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
