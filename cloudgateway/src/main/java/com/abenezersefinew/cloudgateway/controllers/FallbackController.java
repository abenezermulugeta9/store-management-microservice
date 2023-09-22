package com.abenezersefinew.cloudgateway.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    /** If the circuit breaker found the order service down, this method gets called and executed. */
    @GetMapping("order-service-fallback")
    public String orderServiceFallback() {
        return "Order service down!";
    }

    /** If the circuit breaker found the product service down, this method gets called and executed. */
    @GetMapping("product-service-fallback")
    public String productServiceFallback() {
        return "Product service down!";
    }

    /** If the circuit breaker found the payment service down, this method gets called and executed. */
    @GetMapping("payment-service-fallback")
    public String paymentServiceFallback() {
        return "Payment service down!";
    }
}
