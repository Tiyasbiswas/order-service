package com.demo.order_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
/*
@Configuration tells Spring this class contains application configuration.
@Bean registers the REST client with Spring.
RestClient lets Order Service send HTTP requests to Product Service.
 */
@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }
}