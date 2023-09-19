package com.abenezersefinew.orderservice.external.responses;

import com.abenezersefinew.orderservice.models.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetails {
    private Long paymentId;
    private String status;
    private PaymentMode paymentMode;
    private Long amount;
    private Instant paymentDate;
    private Long orderId;
}
