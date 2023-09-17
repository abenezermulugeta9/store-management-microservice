package com.abenezersefinew.orderservice.external.decoders;

import com.abenezersefinew.orderservice.exceptions.GenericException;
import com.abenezersefinew.orderservice.external.responses.ExceptionResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class CustomExceptionDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper objectMapper = new ObjectMapper();

        /** Logs the response url that the exception occurred. */
        log.info("::{}", response.request().url());
        /** Logs the response headers information. */
        log.info("::{}", response.request().headers());

        try {
            /** Reads the value from the response body and parse the result in the ExceptionResponseModel (clone of the exception object in the product service). */
            ExceptionResponseModel exceptionResponseModel = objectMapper.readValue(response.body().asInputStream(), ExceptionResponseModel.class);
            return new GenericException(exceptionResponseModel.getExceptionMessage(), exceptionResponseModel.getExceptionCode(), response.status());
        } catch (IOException e) {
            throw new GenericException("Internal Server Error.", "INTERNAL_SERVER_ERROR", 500);
        }
    }
}
