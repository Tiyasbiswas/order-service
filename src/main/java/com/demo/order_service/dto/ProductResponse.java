package com.demo.order_service.dto;

import java.math.BigDecimal;

/*Why DTO? It represents the product information received from Product Service without
 making Order Service depend directly on Product Service's entity class.*/
public class ProductResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer quantity;

    public ProductResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}