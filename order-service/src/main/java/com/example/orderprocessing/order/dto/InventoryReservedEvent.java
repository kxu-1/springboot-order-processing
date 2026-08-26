package com.example.orderprocessing.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryReservedEvent {
    private String eventId;
    private String eventType;
    private Long orderId;
    private Long productId;
    private Integer reservedQuantity;
    private Integer remainingQuantity;
    private String createdAt;
}
