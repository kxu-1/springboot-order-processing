package com.example.orderprocessing.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    private String eventId;
    private String eventType;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private String createdAt;
}
