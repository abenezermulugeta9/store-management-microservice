package com.abenezersefinew.orderservice.external.clients;

import com.abenezersefinew.orderservice.exceptions.GenericException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The application name of the feign client should be the same as in the configuration file of the product service.
 * The request mapping configuration should be the same as in the controller of the product service.
 */
@FeignClient(name = "PRODUCT-SERVICE/products")
@CircuitBreaker(name = "external", fallbackMethod = "circuitBreakerFallback") /** Configure circuit breaker for the external service calls to product service. */
public interface ProductService {
    /**
     * The method declaration should be the same as in the controller in the product service.
     * Because this is an external call, it needs to have the same api in order to establish a connection between the two services.
     * @params - required productId and quantity query parameter.
     */
    @PutMapping("/reduce-quantity/{id}")
    ResponseEntity<Void> reduceQuantity(@PathVariable("id") Long productId, @RequestParam Long quantity);

    /** This method gets executed when the circuit breaker doesn't find the service unavailable. */
    default ResponseEntity<Void> circuitBreakerFallBack(Exception exception) {
        throw new GenericException("Product service unavailable.", "UNAVAILABLE", 500);
    }
}
