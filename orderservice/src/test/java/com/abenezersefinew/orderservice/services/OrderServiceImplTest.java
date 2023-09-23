package com.abenezersefinew.orderservice.services;


import com.abenezersefinew.orderservice.entities.Order;
import com.abenezersefinew.orderservice.exceptions.GenericException;
import com.abenezersefinew.orderservice.external.clients.PaymentService;
import com.abenezersefinew.orderservice.external.clients.ProductService;
import com.abenezersefinew.orderservice.external.responses.PaymentDetails;
import com.abenezersefinew.orderservice.external.responses.ProductDetails;
import com.abenezersefinew.orderservice.models.OrderResponseModel;
import com.abenezersefinew.orderservice.models.PaymentMode;
import com.abenezersefinew.orderservice.repositories.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceImplTest {
    private static final Instant DATE_TIME_NOW = Instant.now();
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductService productService;
    @Mock
    private PaymentService paymentService;
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    @DisplayName("Get Order Details - Happy Path")
    void testGetOrderDetails() {
        // Mock different calls in the method
        Order order = getOrder();
        when(orderRepository.findById(any(Long.class))).thenReturn(Optional.of(order));
        when(restTemplate.getForObject("http://PRODUCT-SERVICE/products/" + order.getProductId(), ProductDetails.class)).thenReturn(getProductDetails());
        when(restTemplate.getForObject("http://PAYMENT-SERVICE/payments/orders/" + order.getId(), PaymentDetails.class)).thenReturn(getPaymentDetails());

        // Execute the method planned to test
        OrderResponseModel orderResponseModel = orderService.getOrderDetails(1L);

        // Verify if different calls have been made
        verify(orderRepository, times(1)).findById(any(Long.class));
        verify(restTemplate, times(1)).getForObject("http://PRODUCT-SERVICE/products/" + order.getProductId(), ProductDetails.class);
        verify(restTemplate, times(1)).getForObject("http://PAYMENT-SERVICE/payments/orders/" + order.getId(), PaymentDetails.class);

        // Assert values and outcomes
        assertNotNull(orderResponseModel);
        assertEquals(orderResponseModel.getOrderId(), order.getId());
        assertEquals(orderResponseModel.getPaymentDetails(), getPaymentDetails());
    }

    @Test
    @DisplayName("Get Order Details - Throwing Not Found Exception")
    void testGetOrderDetailsThrowingNotFoundException() {
        // Mock different calls in the method
        when(orderRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(null));

        // Execute the method planned to test and throw exception from it
        GenericException actualException = assertThrows(GenericException.class, () -> orderService.getOrderDetails(1L));

        // Verify if different calls have been made
        verify(orderRepository, times(1)).findById(1L);
        verify(restTemplate, never()).getForObject("http://PRODUCT-SERVICE/products/" + getOrder().getProductId(), ProductDetails.class);
        verify(restTemplate, never()).getForObject("http://PAYMENT-SERVICE/payments/orders/" + getOrder().getId(), PaymentDetails.class);

        // Assert values and outcomes
        assertEquals(actualException.getMessage(), "Order not found.");
        assertEquals(actualException.getExceptionCode(), "NOT_FOUND");
        assertEquals(actualException.getHttpStatus(), 404);
    }

    private PaymentDetails getPaymentDetails() {
        return PaymentDetails.builder()
                .paymentId(1L)
                .paymentDate(DATE_TIME_NOW)
                .paymentMode(PaymentMode.CASH)
                .amount(200L)
                .orderId(1L)
                .status("ACCEPTED")
                .build();
    }

    private ProductDetails getProductDetails() {
        return ProductDetails.builder()
                .productId(2L)
                .productName("iPhone 15")
                .price(1000L)
                .quantity(200L)
                .build();
    }

    private Order getOrder() {
        return Order.builder()
                .orderStatus("PLACED")
                .orderDate(DATE_TIME_NOW)
                .id(1L)
                .amount(1000L)
                .quantity(200L)
                .productId(2L)
                .build();
    }
}