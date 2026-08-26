package com.example.orderprocessing.order.mapper;

import com.example.orderprocessing.order.dto.OrderDto;
import com.example.orderprocessing.order.entity.Order;

public class OrderMapper {
    public static OrderDto mapToOrderDto(Order o) {
        if (o == null) return null;
        return new OrderDto(
                o.getId(),
                o.getProductId(),
                o.getQuantity(),
                o.getShippingAddress(),
                o.getStatus(),
                o.getShipmentId(),
                o.getCreatedAt()
        );
    }
}
