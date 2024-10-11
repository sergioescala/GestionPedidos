package com.example.orderservice.service;

import com.example.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

   @Mock
   private OrderRepository orderRepository;

   @InjectMocks
   private OrderService orderService;

   @BeforeEach
   public void setUp() {
       MockitoAnnotations.openMocks(this);
   }

   @Test
   public void testCreateOrder() {
       Order order = new Order();
       order.setCustomerId(1L);
       order.setProductId(101L);
       order.setStatus("pendiente");

       when(orderRepository.save(any(Order.class))).thenReturn(order);

       Order createdOrder = orderService.createOrder(order);

       assertNotNull(createdOrder);
       assertEquals("pendiente", createdOrder.getStatus());
       verify(orderRepository, times(1)).save(order);
   }

   @Test
   public void testGetOrderById_Success() {
       Order order = new Order();
       order.setId(1L);

       when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

       Order foundOrder = orderService.getOrderById(1L);

       assertNotNull(foundOrder);
       assertEquals(1L, foundOrder.getId());
       verify(orderRepository, times(1)).findById(1L);
   }

   @Test
   public void testGetOrderById_NotFound() {
       when(orderRepository.findById(1L)).thenReturn(Optional.empty());

       assertThrows(OrderNotFoundException.class, () -> {
           orderService.getOrderById(1L);
       });

       verify(orderRepository, times(1)).findById(1L);
   }

   // Otros tests para updateOrderStatus y deleteOrder
}
