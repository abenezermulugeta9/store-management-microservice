package com.abenezersefinew.orderservice;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * This class appears to be an implementation of the ServiceInstanceListSupplier interface
 * in a context related to service discovery and load balancing within a microservice's
 * architecture. This class provides a hard-coded list of service instances.
 * */
public class TestServiceInstanceListSupplier implements ServiceInstanceListSupplier {
    @Override
    public String getServiceId() {
        return null;
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        List<ServiceInstance> result = new ArrayList<>();
        result.add(new DefaultServiceInstance("PAYMENT-SERVICE", "PAYMENT-SERVICE", "localhost", 8080, false));
        result.add(new DefaultServiceInstance("PRODUCT-SERVICE", "PRODUCT-SERVICE", "localhost", 8080, false));
        return Flux.just(result);
    }
}
