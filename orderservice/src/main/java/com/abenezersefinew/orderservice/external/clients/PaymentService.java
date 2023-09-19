package com.abenezersefinew.orderservice.external.clients;

import com.abenezersefinew.orderservice.external.requests.PaymentRequestModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The application name of the feign client should be the same as in the configuration file of the payment service.
 * The request mapping configuration should be the same as in the controller of the payment service.
 */
@FeignClient("PAYMENT-SERVICE/payments")
public interface PaymentService {
    /**
     * The method declaration should be the same as in the controller in the payment service.
     * Because this is an external call, it needs to have the same api in order to establish a connection between the two services.
     * @param- the request body is a dto the same as we have in the product service.
     */
    @PostMapping
    ResponseEntity<Long> processPayment(@RequestBody PaymentRequestModel paymentRequestModel);
}
