package com.abenezersefinew.productservice.exceptions.handlers;

import com.abenezersefinew.productservice.exceptions.ProductNotFoundException;
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
                .httpStatus(HttpStatus.NOT_FOUND)
                .exceptionMessage(exception.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseModel> handleGenericException(Exception exception) {
        return new ResponseEntity<>(new ExceptionResponseModel().builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .exceptionMessage(exception.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
