package com.example.orderprocessing.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private Long productId;
    private Integer quantity;
    private String shippingAddress;
    private String status; // PENDING, CONFIRMED
    private Long shipmentId;
    private LocalDateTime createdAt;
}
