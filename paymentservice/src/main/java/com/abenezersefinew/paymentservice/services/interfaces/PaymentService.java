package com.abenezersefinew.paymentservice.services.interfaces;

import com.abenezersefinew.paymentservice.models.PaymentRequestModel;
import com.abenezersefinew.paymentservice.models.PaymentResponseModel;

public interface PaymentService {
    Long processPayment(PaymentRequestModel paymentRequestModel);
    PaymentResponseModel getPaymentDetailsByOrderId(Long orderId);
}
