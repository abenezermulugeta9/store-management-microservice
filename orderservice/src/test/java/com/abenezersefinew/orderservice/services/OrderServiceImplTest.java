package com.abenezersefinew.orderservice.services;


import com.abenezersefinew.orderservice.entities.Order;
import com.abenezersefinew.orderservice.exceptions.GenericException;
import com.abenezersefinew.orderservice.external.clients.PaymentService;
import com.abenezersefinew.orderservice.external.clients.ProductService;
import com.abenezersefinew.orderservice.external.requests.PaymentRequestModel;
import com.abenezersefinew.orderservice.external.responses.PaymentDetails;
import com.abenezersefinew.orderservice.external.responses.ProductDetails;
import com.abenezersefinew.orderservice.models.OrderRequestModel;
import com.abenezersefinew.orderservice.models.OrderResponseModel;
import com.abenezersefinew.orderservice.models.PaymentMode;
import com.abenezersefinew.orderservice.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceImplTest {
    private Order order;
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

    @BeforeEach
    void setup() {
        // This method gets called in every test method, so better call it here once for all.
        order = getMockOrder();
    }

    @Test
    @DisplayName("Get order details the happy path")
    void testGetOrderDetails() {
        // Mock different calls and objects in the method
        // Some mocks come from the @BeforeEach method
        when(orderRepository.findById(any(Long.class))).thenReturn(Optional.of(order));
        when(restTemplate.getForObject("http://PRODUCT-SERVICE/products/" + order.getProductId(), ProductDetails.class)).thenReturn(getMockProductDetails());
        when(restTemplate.getForObject("http://PAYMENT-SERVICE/payments/orders/" + order.getId(), PaymentDetails.class)).thenReturn(getMockPaymentDetails());

        // Execute the method planned to test
        OrderResponseModel orderResponseModel = orderService.getOrderDetails(1L);

        // Verify if different calls have been made
        verify(orderRepository, times(1)).findById(any(Long.class));
        verify(restTemplate, times(1)).getForObject("http://PRODUCT-SERVICE/products/" + order.getProductId(), ProductDetails.class);
        verify(restTemplate, times(1)).getForObject("http://PAYMENT-SERVICE/payments/orders/" + order.getId(), PaymentDetails.class);

        // Assert values and outcomes
        assertNotNull(orderResponseModel);
        assertEquals(orderResponseModel.getOrderId(), order.getId());
        assertEquals(orderResponseModel.getPaymentDetails(), getMockPaymentDetails());
    }

    @Test
    @DisplayName("Get order details throws not found exception")
    void testGetOrderDetailsThrowingNotFoundException() {
        // Mock different calls and objects in the method
        // Some mocks come from the @BeforeEach method
        when(orderRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(null));

        // Execute the method planned to test and throw exception from it
        GenericException actualException = assertThrows(GenericException.class, () -> orderService.getOrderDetails(1L));

        // Verify if different calls have been made
        verify(orderRepository, times(1)).findById(1L);
        verify(restTemplate, never()).getForObject("http://PRODUCT-SERVICE/products/" + order.getProductId(), ProductDetails.class);
        verify(restTemplate, never()).getForObject("http://PAYMENT-SERVICE/payments/orders/" + order.getId(), PaymentDetails.class);

        // Assert values and outcomes
        assertEquals(actualException.getMessage(), "Order not found.");
        assertEquals(actualException.getExceptionCode(), "NOT_FOUND");
        assertEquals(actualException.getHttpStatus(), 404);
    }

    @Test
    @DisplayName("Place order the happy path")
    void testPlaceOrder() {
        // Mock different calls and objects in the method
        // Some mocks come from the @BeforeEach method
        OrderRequestModel orderRequestModel = getMockOrderRequestModel();
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(productService.reduceQuantity(any(Long.class), any(Long.class))).thenReturn(new ResponseEntity<Void>(HttpStatus.OK));
        when(paymentService.processPayment(any(PaymentRequestModel.class))).thenReturn(new ResponseEntity<Long>(HttpStatus.OK));

        // Execute the method planned to test
        Long orderId = orderService.placeOrder(orderRequestModel);

        // Verify if different calls have been made
        verify(orderRepository, times(2)).save(any(Order.class));
        verify(productService, times(1)).reduceQuantity(any(Long.class), any(Long.class));
        verify(paymentService, times(1)).processPayment(any(PaymentRequestModel.class));

        // Assert values and outcomes
        assertEquals(orderId, order.getId());
    }

    @Test
    @DisplayName("Place order creates the order but failed to process payment")
    void testProcessPaymentFailureAndOrderCreated() {
        // Mock different calls and objects in the method
        // Some mocks come from the @BeforeEach method
        OrderRequestModel orderRequestModel = getMockOrderRequestModel();
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(productService.reduceQuantity(any(Long.class), any(Long.class))).thenReturn(new ResponseEntity<Void>(HttpStatus.OK));
        when(paymentService.processPayment(any(PaymentRequestModel.class))).thenThrow(new RuntimeException());

        // Execute the method planned to test
        Long orderId = orderService.placeOrder(orderRequestModel);

        // Verify if different calls have been made
        verify(orderRepository, times(2)).save(any(Order.class));
        verify(productService, times(1)).reduceQuantity(any(Long.class), any(Long.class));
        verify(paymentService, times(1)).processPayment(any(PaymentRequestModel.class));

        // Assert values and outcomes
        assertEquals(orderId, order.getId());
    }

    private PaymentDetails getMockPaymentDetails() {
        return PaymentDetails.builder()
                .paymentId(1L)
                .paymentDate(DATE_TIME_NOW)
                .paymentMode(PaymentMode.CASH)
                .amount(200L)
                .orderId(1L)
                .status("ACCEPTED")
                .build();
    }

    private ProductDetails getMockProductDetails() {
        return ProductDetails.builder()
                .productId(2L)
                .productName("iPhone 15")
                .price(1000L)
                .quantity(200L)
                .build();
    }

    private OrderRequestModel getMockOrderRequestModel() {
        return OrderRequestModel.builder()
                .productId(2L)
                .paymentMode(PaymentMode.CASH)
                .quantity(200L)
                .totalAmount(1000L)
                .build();
    }

    private Order getMockOrder() {
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