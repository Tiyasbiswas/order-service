
package com.demo.order_service.service;

import com.demo.order_service.client.ProductClient;
import com.demo.order_service.dto.ProductResponse;
import com.demo.order_service.entity.Order;
import com.demo.order_service.repository.OrderRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrderSuccessfully() {

        ProductResponse product = new ProductResponse();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(new BigDecimal("999.99"));
        product.setQuantity(10);

        Order order = new Order();
        order.setProductId(1L);
        order.setQuantity(2);

        when(productClient.getProductById(1L)).thenReturn(product);
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order savedOrder = orderService.createOrder(order);

        assertEquals(new BigDecimal("1999.98"), savedOrder.getTotalPrice());
        assertEquals("CREATED", savedOrder.getStatus());

        verify(productClient).getProductById(1L);
        verify(orderRepository).save(order);
    }

    @Test
    void createOrderWithInsufficientStock() {

        ProductResponse product = new ProductResponse();
        product.setPrice(new BigDecimal("999.99"));
        product.setQuantity(1);

        Order order = new Order();
        order.setProductId(1L);
        order.setQuantity(2);

        when(productClient.getProductById(1L)).thenReturn(product);

        assertThrows(
                IllegalArgumentException.class,
                () -> orderService.createOrder(order)
        );

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void createOrderWithZeroQuantity() {

        ProductResponse product = new ProductResponse();
        product.setPrice(new BigDecimal("999.99"));
        product.setQuantity(10);

        Order order = new Order();
        order.setProductId(1L);
        order.setQuantity(0);

        when(productClient.getProductById(1L)).thenReturn(product);

        assertThrows(
                IllegalArgumentException.class,
                () -> orderService.createOrder(order)
        );

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void createOrderWithNegativeQuantity() {

        ProductResponse product = new ProductResponse();
        product.setPrice(new BigDecimal("999.99"));
        product.setQuantity(10);

        Order order = new Order();
        order.setProductId(1L);
        order.setQuantity(-2);

        when(productClient.getProductById(1L)).thenReturn(product);

        assertThrows(
                IllegalArgumentException.class,
                () -> orderService.createOrder(order)
        );

        verify(orderRepository, never()).save(any(Order.class));
    }
}
