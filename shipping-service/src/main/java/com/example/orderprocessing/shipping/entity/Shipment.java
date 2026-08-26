package com.example.orderprocessing.shipping.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {
    private Long id;
    private Long orderId;
    private String shippingAddress;
    private String status; // CREATED
    private LocalDateTime createdAt;
}
