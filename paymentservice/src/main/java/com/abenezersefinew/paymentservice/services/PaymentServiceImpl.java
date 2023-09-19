package com.abenezersefinew.paymentservice.services;

import com.abenezersefinew.paymentservice.entities.TransactionDetails;
import com.abenezersefinew.paymentservice.models.PaymentRequestModel;
import com.abenezersefinew.paymentservice.respositories.TransactionDetailsRepository;
import com.abenezersefinew.paymentservice.services.interfaces.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {
    private final TransactionDetailsRepository transactionDetailsRepository;

    @Autowired
    public PaymentServiceImpl(TransactionDetailsRepository transactionDetailsRepository) {
        this.transactionDetailsRepository = transactionDetailsRepository;
    }
    @Override
    public Long processPayment(PaymentRequestModel paymentRequestModel) {
        log.info("Processing payment...");
        TransactionDetails transactionDetails = TransactionDetails.builder()
                .paymentDate(Instant.now())
                .paymentMode(paymentRequestModel.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .orderId(paymentRequestModel.getOrderId())
                .referenceNumber(paymentRequestModel.getReferenceNumber())
                .amount(paymentRequestModel.getAmount())
                .build();

        transactionDetails = transactionDetailsRepository.save(transactionDetails);
        log.info("Payment transaction completed.");
        return transactionDetails.getId();
    }
}
