package com.example.orderprocessing.order.producer;

import com.example.orderprocessing.order.dto.OrderCreatedEvent;
import com.example.orderprocessing.order.util.SimpleLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "order-events";

    public void publishOrderCreated(OrderCreatedEvent event) {
        String key = event.getOrderId().toString();
        kafkaTemplate.send(TOPIC, key, event);
        SimpleLogger.info("Published ORDER_CREATED event for order {}", event.getOrderId());
    }
}
