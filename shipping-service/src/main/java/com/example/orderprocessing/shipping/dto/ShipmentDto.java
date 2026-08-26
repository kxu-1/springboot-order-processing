package com.example.orderprocessing.shipping.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDto {
    private Long id;
    private Long orderId;
    private String shippingAddress;
    private String status;
    private LocalDateTime createdAt;
}
