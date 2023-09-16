package com.abenezersefinew.productservice.models;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponseModel {
    private HttpStatus httpStatus;
    private String exceptionMessage;
}
