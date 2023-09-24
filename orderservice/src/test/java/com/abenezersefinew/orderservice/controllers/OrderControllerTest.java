package com.abenezersefinew.orderservice.controllers;

import com.abenezersefinew.orderservice.OrderServiceConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest({"server.port = 0"}) // The port definition used to specify that the test should run on a dynamically assigned random port.
@EnableConfigurationProperties // Binds external configuration properties to Java objects.
@AutoConfigureMockMvc // Simulates HTTP requests to your controller without actually starting a web server.
@ContextConfiguration(classes = { OrderServiceConfig.class }) // Specifies the configuration of service instances list.
public class OrderControllerTest {

}