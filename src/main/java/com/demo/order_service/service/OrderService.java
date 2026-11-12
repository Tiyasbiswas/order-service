package com.demo.order_service.service;


import com.demo.order_service.client.ProductClient;
import com.demo.order_service.dto.ProductResponse;
import com.demo.order_service.entity.Order;
import com.demo.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    public OrderService(OrderRepository orderRepository, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
    }

//    public Order createOrder(Order order) {
//        order.setStatus("CREATED");
//        return orderRepository.save(order);
//    }
public Order createOrder(Order order) {
    ProductResponse product =
            productClient.getProductById(order.getProductId());
    if (order.getQuantity() == null || order.getQuantity() <= 0) {
        throw new IllegalArgumentException("Order quantity must be greater than zero");
    }
    if (product.getQuantity() < order.getQuantity()) {
        throw new IllegalArgumentException("Insufficient product stock");
    }

    BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(order.getQuantity()));
    order.setTotalPrice(totalPrice);
    order.setStatus("CREATED");
    return orderRepository.save(order);
}
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found: " + id));
    }
}
