package com.example.orderprocessing.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private Long productId;
    private Integer quantity;
    private String shippingAddress;
    private String status;
    private Long shipmentId;
    private LocalDateTime createdAt;
}
