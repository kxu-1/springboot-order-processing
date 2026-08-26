package com.example.orderprocessing.shipping.service.impl;

import com.example.orderprocessing.shipping.dto.ShipmentDto;
import com.example.orderprocessing.shipping.dto.ShipmentRequest;
import com.example.orderprocessing.shipping.entity.Shipment;
import com.example.orderprocessing.shipping.exception.ResourceNotFoundException;
import com.example.orderprocessing.shipping.mapper.ShipmentMapper;
import com.example.orderprocessing.shipping.service.ShipmentService;
import com.example.orderprocessing.shipping.util.SimpleLogger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final Map<Long, Shipment> shipments = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(5000); // Generates starting from 5001

    @Override
    public ShipmentDto createShipment(ShipmentRequest request) {
        SimpleLogger.info("Received shipping request for order {}", request.getOrderId());
        long id = idGenerator.incrementAndGet();
        Shipment shipment = new Shipment(
                id,
                request.getOrderId(),
                request.getShippingAddress(),
                "CREATED",
                LocalDateTime.now()
        );
        shipments.put(id, shipment);
        SimpleLogger.info("Shipment {} created", id);
        return ShipmentMapper.mapToShipmentDto(shipment);
    }

    @Override
    public List<ShipmentDto> getAllShipments() {
        SimpleLogger.info("Fetching all shipments");
        return shipments.values().stream()
                .map(ShipmentMapper::mapToShipmentDto)
                .collect(Collectors.toList());
    }

    @Override
    public ShipmentDto getShipmentById(Long id) {
        SimpleLogger.info("Fetching shipment with ID: {}", id);
        Shipment shipment = shipments.get(id);
        if (shipment == null) {
            throw new ResourceNotFoundException("Shipment with ID " + id + " does not exist");
        }
        return ShipmentMapper.mapToShipmentDto(shipment);
    }

    @Override
    public ShipmentDto updateShipment(Long id, ShipmentRequest request) {
        SimpleLogger.info("Updating shipment with ID: {}", id);
        Shipment shipment = shipments.get(id);
        if (shipment == null) {
            throw new ResourceNotFoundException("Shipment with ID " + id + " does not exist");
        }
        shipment.setOrderId(request.getOrderId());
        shipment.setShippingAddress(request.getShippingAddress());
        return ShipmentMapper.mapToShipmentDto(shipment);
    }

    @Override
    public void deleteShipment(Long id) {
        SimpleLogger.info("Deleting shipment with ID: {}", id);
        if (!shipments.containsKey(id)) {
            throw new ResourceNotFoundException("Shipment with ID " + id + " does not exist");
        }
        shipments.remove(id);
    }
}
