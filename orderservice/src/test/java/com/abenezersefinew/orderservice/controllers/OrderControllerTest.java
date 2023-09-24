package com.abenezersefinew.orderservice.controllers;

import com.abenezersefinew.orderservice.OrderServiceConfig;
import com.abenezersefinew.orderservice.repositories.OrderRepository;
import com.abenezersefinew.orderservice.services.interfaces.OrderService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

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
}