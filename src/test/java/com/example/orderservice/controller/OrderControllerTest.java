package com.example.orderservice.controller;

import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OrderControllerTest {

   private MockMvc mockMvc;

   @Mock
   private OrderService orderService;

   @InjectMocks
   private OrderController orderController;

   @BeforeEach
   public void setUp() {
       MockitoAnnotations.openMocks(this);
       mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
   }

   @Test
   public void testCreateOrder() throws Exception {
       Order order = new Order();
       order.setId(1L);
       order.setCustomerId(1L);
       order.setProductId(101L);
       order.setStatus("pendiente");

       when(orderService.createOrder(any(Order.class))).thenReturn(order);

       mockMvc.perform(post("/orders")
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(order)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1));

       verify(orderService, times(1)).createOrder(any(Order.class));
   }

   // Otros tests para getOrderById, getAllOrders, updateOrderStatus y deleteOrder

   private static String asJsonString(final Object obj) {
       try {
           return new ObjectMapper().writeValueAsString(obj);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
   }
}
