package com.example.orderprocessing.product.producer;

import com.example.orderprocessing.product.dto.InventoryReservedEvent;
import com.example.orderprocessing.product.util.SimpleLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "inventory-events";

    public void publishInventoryReserved(InventoryReservedEvent event) {
        String key = event.getOrderId().toString();
        kafkaTemplate.send(TOPIC, key, event);
        SimpleLogger.info("Published INVENTORY_RESERVED event for order {}", event.getOrderId());
    }
}
