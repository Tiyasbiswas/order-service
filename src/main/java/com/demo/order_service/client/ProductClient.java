package com.demo.order_service.client;

import com.demo.order_service.dto.ProductResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
/*
- @Component registers ProductClient with Spring.
- RestClient sends HTTP requests.
- .get() makes a GET request.
- .uri() specifies the Product Service endpoint.
- .body(ProductResponse.class) converts the JSON response into our DTO.
 */
@Component
public class ProductClient {

    private final RestClient restClient;

    public ProductClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public ProductResponse getProductById(Long productId) {
        return restClient.get()
                .uri("http://product-service:8081/api/products/{id}", productId)                .retrieve()
                .body(ProductResponse.class);
    }
}