package com.example.orderprocessing.order.consumer;

import com.example.orderprocessing.order.dto.InventoryReservedEvent;
import com.example.orderprocessing.order.service.OrderService;
import com.example.orderprocessing.order.util.SimpleLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryEventConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = "inventory-events", groupId = "order-service-group")
    public void consumeInventoryReserved(InventoryReservedEvent event) {
        SimpleLogger.info("Received INVENTORY_RESERVED event for order {}", event.getOrderId());
        orderService.confirmOrderAndShip(event.getOrderId());
    }
}
