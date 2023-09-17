package com.abenezersefinew.orderservice.config;

import com.abenezersefinew.orderservice.external.decoders.CustomExceptionDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    ErrorDecoder errorDecoder() {
        return new CustomExceptionDecoder();
    }
}
