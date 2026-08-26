package com.example.orderprocessing.shipping.service;

import com.example.orderprocessing.shipping.dto.ShipmentDto;
import com.example.orderprocessing.shipping.dto.ShipmentRequest;

import java.util.List;

public interface ShipmentService {
    ShipmentDto createShipment(ShipmentRequest request);
    List<ShipmentDto> getAllShipments();
    ShipmentDto getShipmentById(Long id);
    ShipmentDto updateShipment(Long id, ShipmentRequest request);
    void deleteShipment(Long id);
}
