package com.example.orderprocessing.order.service;

import com.example.orderprocessing.order.dto.OrderDto;
import com.example.orderprocessing.order.dto.OrderRequest;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderRequest orderRequest);
    List<OrderDto> getAllOrders();
    OrderDto getOrderById(Long id);
    void confirmOrderAndShip(Long orderId);
}
