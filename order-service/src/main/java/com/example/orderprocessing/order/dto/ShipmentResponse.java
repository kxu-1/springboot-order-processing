package com.example.orderprocessing.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentResponse {
    private Long id;
    private Long orderId;
    private String shippingAddress;
    private String status;
}
