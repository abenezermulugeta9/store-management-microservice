package com.abenezersefinew.orderservice.external.responses;

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
