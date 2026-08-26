package com.example.orderprocessing.shipping.mapper;

import com.example.orderprocessing.shipping.dto.ShipmentDto;
import com.example.orderprocessing.shipping.entity.Shipment;

public class ShipmentMapper {
    public static ShipmentDto mapToShipmentDto(Shipment s) {
        if (s == null) return null;
        return new ShipmentDto(
                s.getId(),
                s.getOrderId(),
                s.getShippingAddress(),
                s.getStatus(),
                s.getCreatedAt()
        );
    }
}
