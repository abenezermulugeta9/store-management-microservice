package com.abenezersefinew.productservice.models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponseModel {
    private String exceptionCode;
    private String exceptionMessage;
}
