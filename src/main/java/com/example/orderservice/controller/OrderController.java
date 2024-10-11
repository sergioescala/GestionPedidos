package com.example.orderservice.controller;

import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

   private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

   private final OrderService orderService;

   public OrderController(OrderService orderService) {
       this.orderService = orderService;
   }

   @PostMapping
   public Order createOrder(@RequestBody Order order) {
       logger.info("Creando nuevo pedido: {}", order);
       return orderService.createOrder(order);
   }

   @GetMapping("/{id}")
   public Order getOrderById(@PathVariable Long id) {
       logger.info("Consultando pedido con ID: {}", id);
       return orderService.getOrderById(id);
   }

   @GetMapping
   public List<Order> getAllOrders() {
       logger.info("Listando todos los pedidos");
       return orderService.getAllOrders();
   }

   @PutMapping("/{id}/status")
   public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
       logger.info("Actualizando estado del pedido con ID: {} a '{}'", id, status);
       Order order = orderService.updateOrderStatus(id, status);
       if (order == null) {
           return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(order);
   }

   @DeleteMapping("/{id}")
   public void deleteOrder(@PathVariable Long id) {
       logger.info("Eliminando pedido con ID: {}", id);
       orderService.deleteOrder(id);
   }
}
