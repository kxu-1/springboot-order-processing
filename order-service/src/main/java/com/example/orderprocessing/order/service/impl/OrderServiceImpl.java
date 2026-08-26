package com.example.orderprocessing.order.service.impl;

import com.example.orderprocessing.order.client.ShippingClient;
import com.example.orderprocessing.order.dto.OrderCreatedEvent;
import com.example.orderprocessing.order.dto.OrderDto;
import com.example.orderprocessing.order.dto.OrderRequest;
import com.example.orderprocessing.order.dto.ShipmentResponse;
import com.example.orderprocessing.order.entity.Order;
import com.example.orderprocessing.order.exception.ResourceNotFoundException;
import com.example.orderprocessing.order.mapper.OrderMapper;
import com.example.orderprocessing.order.producer.OrderEventProducer;
import com.example.orderprocessing.order.service.OrderService;
import com.example.orderprocessing.order.util.SimpleLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final Map<Long, Order> orders = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1000); // Generates starting from 1001
    private final OrderEventProducer eventProducer;
    private final ShippingClient shippingClient;

    @Override
    public OrderDto createOrder(OrderRequest orderRequest) {
        long id = idGenerator.incrementAndGet();
        Order order = new Order(
                id,
                orderRequest.getProductId(),
                orderRequest.getQuantity(),
                orderRequest.getShippingAddress(),
                "PENDING",
                null,
                LocalDateTime.now()
        );
        orders.put(id, order);
        SimpleLogger.info("Order {} created with status PENDING", id);

        OrderCreatedEvent event = new OrderCreatedEvent(
                UUID.randomUUID().toString(),
                "ORDER_CREATED",
                id,
                order.getProductId(),
                order.getQuantity(),
                order.getCreatedAt().toString()
        );
        eventProducer.publishOrderCreated(event);

        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        SimpleLogger.info("Fetching all orders");
        return orders.values().stream()
                .map(OrderMapper::mapToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(Long id) {
        SimpleLogger.info("Fetching order with ID: {}", id);
        Order order = orders.get(id);
        if (order == null) {
            throw new ResourceNotFoundException("Order with ID " + id + " does not exist");
        }
        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    public void confirmOrderAndShip(Long orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            SimpleLogger.error("Order with ID {} not found for confirmation", orderId);
            return;
        }

        order.setStatus("CONFIRMED");
        SimpleLogger.info("Order {} updated to CONFIRMED", orderId);

        ShipmentResponse response = shippingClient.createShipment(orderId, order.getShippingAddress());
        if (response != null) {
            order.setShipmentId(response.getId());
        }
    }
}
