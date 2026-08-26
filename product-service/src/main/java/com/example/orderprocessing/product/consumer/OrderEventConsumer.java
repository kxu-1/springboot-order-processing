package com.example.orderprocessing.product.consumer;

import com.example.orderprocessing.product.dto.OrderCreatedEvent;
import com.example.orderprocessing.product.service.ProductService;
import com.example.orderprocessing.product.util.SimpleLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final ProductService productService;

    @KafkaListener(topics = "order-events", groupId = "product-service-group")
    public void consumeOrderCreated(OrderCreatedEvent event) {
        SimpleLogger.info("Received ORDER_CREATED event for order {}", event.getOrderId());
        productService.reserveInventory(event.getProductId(), event.getQuantity(), event.getOrderId());
    }
}
