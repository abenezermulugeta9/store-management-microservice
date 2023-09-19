package com.abenezersefinew.orderservice.services;

import com.abenezersefinew.orderservice.entities.Order;
import com.abenezersefinew.orderservice.external.clients.PaymentService;
import com.abenezersefinew.orderservice.external.clients.ProductService;
import com.abenezersefinew.orderservice.external.requests.PaymentRequestModel;
import com.abenezersefinew.orderservice.models.OrderRequestModel;
import com.abenezersefinew.orderservice.repositories.OrderRepository;
import com.abenezersefinew.orderservice.services.interfaces.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final PaymentService paymentService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService, PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.paymentService = paymentService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public Long placeOrder(OrderRequestModel orderRequestModel) {
        log.info("Placing order...");

        /** Create order with "CREATED" order status. */
        Order order = Order.builder()
                .amount(orderRequestModel.getTotalAmount())
                .productId(orderRequestModel.getProductId())
                .quantity(orderRequestModel.getQuantity())
                .orderDate(Instant.now())
                .orderStatus("CREATED")
                .build();
        order = orderRepository.save(order);
        log.info("Order placed.");

        /** @external - reduce product quantity in the product service. */
        log.info("Reducing product inventory in product service...");
        productService.reduceQuantity(orderRequestModel.getProductId(), orderRequestModel.getQuantity());
        log.info("Product inventory reduced.");

        /** Process payment. */
        log.info("Processing payment...");
        PaymentRequestModel paymentRequestModel = PaymentRequestModel.builder()
                .orderId(order.getId())
                .paymentMode(orderRequestModel.getPaymentMode())
                .amount(orderRequestModel.getTotalAmount())
                .build();
        String orderStatus = null;
        try {
            /** @external - process payment in the payment service. */
            paymentService.processPayment(paymentRequestModel);
            log.info("Payment processed for the order.");
            orderStatus = "PLACED";
        } catch (Exception exception) {
            log.error("Error processing payment.");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        log.info("Order placed successfully.");
        return order.getId();
    }
}
