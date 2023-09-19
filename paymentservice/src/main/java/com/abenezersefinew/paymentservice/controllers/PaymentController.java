package com.abenezersefinew.paymentservice.controllers;

import com.abenezersefinew.paymentservice.models.PaymentRequestModel;
import com.abenezersefinew.paymentservice.services.interfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Long> processPayment(@RequestBody PaymentRequestModel paymentRequestModel) {
        return new ResponseEntity<>(paymentService.processPayment(paymentRequestModel), HttpStatus.CREATED);

    }
}
