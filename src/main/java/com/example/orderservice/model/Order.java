package com.example.orderservice.model;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   private Long customerId;

   @Column(nullable = false)
   private Long productId;

   @Column(nullable = false)
   private String status;

   // Getters y Setters
   public Long getId() {
       return id;
   }
   public void setId(Long id) {
       this.id = id;
   }
   public Long getCustomerId() {
       return customerId;
   }
   public void setCustomerId(Long customerId) {
       this.customerId = customerId;
   }
   public Long getProductId() {
       return productId;
   }
   public void setProductId(Long productId) {
       this.productId = productId;
   }
   public String getStatus() {
       return status;
   }
   public void setStatus(String status) {
       this.status = status;
   }
}
