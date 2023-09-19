package com.abenezersefinew.paymentservice.services.interfaces;

import com.abenezersefinew.paymentservice.models.PaymentRequestModel;

public interface PaymentService {
    Long processPayment(PaymentRequestModel paymentRequestModel);
}
