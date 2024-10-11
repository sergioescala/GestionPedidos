package com.example.orderservice.service;

import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
   private final OrderRepository orderRepository;

   public OrderService(OrderRepository orderRepository) {
       this.orderRepository = orderRepository;
   }

   public Order createOrder(Order order) {
       order.setStatus("pendiente");
       return orderRepository.save(order);
   }

   public Order getOrderById(Long id) {
       return orderRepository.findById(id).orElse(null);
   }

   public List<Order> getAllOrders() {
       return orderRepository.findAll();
   }

   public Order updateOrderStatus(Long id, String status) {
       Order order = getOrderById(id);
       if (order != null) {
           order.setStatus(status);
           return orderRepository.save(order);
       }
       return null;
   }

   public void deleteOrder(Long id) {
       orderRepository.deleteById(id);
   }
}
