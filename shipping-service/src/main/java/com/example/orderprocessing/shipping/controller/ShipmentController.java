package com.example.orderprocessing.shipping.controller;

import com.example.orderprocessing.shipping.dto.ShipmentDto;
import com.example.orderprocessing.shipping.dto.ShipmentRequest;
import com.example.orderprocessing.shipping.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PostMapping
    public ResponseEntity<ShipmentDto> createShipment(@RequestBody ShipmentRequest request) {
        ShipmentDto savedShipment = shipmentService.createShipment(request);
        return new ResponseEntity<>(savedShipment, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ShipmentDto> getShipmentById(@PathVariable("id") Long id) {
        ShipmentDto shipmentDto = shipmentService.getShipmentById(id);
        return ResponseEntity.ok(shipmentDto);
    }

    @GetMapping
    public ResponseEntity<List<ShipmentDto>> getAllShipments() {
        List<ShipmentDto> shipments = shipmentService.getAllShipments();
        return ResponseEntity.ok(shipments);
    }

    @PutMapping("{id}")
    public ResponseEntity<ShipmentDto> updateShipment(@PathVariable("id") Long id,
                                                      @RequestBody ShipmentRequest request) {
        ShipmentDto s = shipmentService.updateShipment(id, request);
        return ResponseEntity.ok(s);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteShipment(@PathVariable("id") Long id) {
        shipmentService.deleteShipment(id);
        return ResponseEntity.ok("Shipment deleted successfully");
    }
}
