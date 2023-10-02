package com.abenezersefinew.orderservice.controllers;

import com.abenezersefinew.orderservice.OrderServiceConfig;
import com.abenezersefinew.orderservice.entities.Order;
import com.abenezersefinew.orderservice.models.OrderRequestModel;
import com.abenezersefinew.orderservice.models.PaymentMode;
import com.abenezersefinew.orderservice.repositories.OrderRepository;
import com.abenezersefinew.orderservice.services.interfaces.OrderService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest({"server.port = 0"}) // The port definition used to specify that the test should run on a dynamically assigned random port.
@EnableConfigurationProperties // Binds external configuration properties to Java objects.
@AutoConfigureMockMvc // Simulates HTTP requests to your controller without actually starting a web server.
@ContextConfiguration(classes = { OrderServiceConfig.class }) // Specifies the configuration of service instances list.
public class OrderControllerTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MockMvc mockMvc;

    /**
     * WireMock is a popular Java library for mocking HTTP-based services.
     * It is often used in testing scenarios where you need to simulate the
     * behavior of external services or APIs without actually making real network
     * requests. WireMock allows you to define stubs or expectations for HTTP
     * requests and specify how the mock server should respond to those requests.
     * */
    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig()
                    .port(8080))
            .build();

    private ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @BeforeEach
    void setup() throws IOException {
        getProductDetailsResponse();
        processPayment();
        getPaymentDetails();
        reduceQuantity();
    }

    // WireMock stubs
    private void reduceQuantity() {
        wireMockServer.stubFor(WireMock.put("/products/reduce-quantity/.*")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));
    }

    private void getPaymentDetails() throws IOException {
        wireMockServer.stubFor(WireMock.get("/payments/.*")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(StreamUtils.copyToString(OrderControllerTest.class.getClassLoader()
                                .getResourceAsStream("mock/GetPayment.json"), Charset.defaultCharset()))));
    }

    private void processPayment() {
        wireMockServer.stubFor(WireMock.post("/payments")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));
    }

    private void getProductDetailsResponse() throws IOException {
        wireMockServer.stubFor(WireMock.get("/products/1")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(StreamUtils.copyToString(OrderControllerTest.class.getClassLoader()
                                .getResourceAsStream("mock/GetProduct.json"), Charset.defaultCharset()))));
    }

    @Test
    @Disabled
    public void testPlaceOrderWithProcessPayment() throws Exception {
        // Place order
        OrderRequestModel orderRequestModel = getMockOrderRequestModel();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/orders/place-order")
                .with(SecurityMockMvcRequestPostProcessors.jwt().authorities(new SimpleGrantedAuthority("Customer")))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(orderRequestModel))
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String orderId = mvcResult.getResponse().getContentAsString();

        // Find value from H2 in-memory database.
        Optional<Order> order = orderRepository.findById(Long.valueOf(orderId));

        // Assertions
        assertTrue(order.isPresent());
        assertEquals(Long.parseLong(orderId), order.get().getId());
        assertEquals(orderRequestModel.getTotalAmount(), order.get().getAmount());
        assertEquals(orderRequestModel.getQuantity(), order.get().getQuantity());
     }

    private OrderRequestModel getMockOrderRequestModel() {
        return OrderRequestModel.builder()
                .productId(1L)
                .paymentMode(PaymentMode.CASH)
                .quantity(10L)
                .totalAmount(200L)
                .build();
    }
}