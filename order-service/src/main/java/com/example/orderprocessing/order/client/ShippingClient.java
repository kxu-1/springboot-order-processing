package com.example.orderprocessing.order.client;

import com.example.orderprocessing.order.dto.ShipmentRequest;
import com.example.orderprocessing.order.dto.ShipmentResponse;
import com.example.orderprocessing.order.util.SimpleLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ShippingClient {

    private final RestTemplate restTemplate;

    @Value("${shipping.service.base-url}")
    private String shippingServiceBaseUrl;

    public ShipmentResponse createShipment(Long orderId, String shippingAddress) {
        ShipmentRequest request = new ShipmentRequest(orderId, shippingAddress);
        String url = shippingServiceBaseUrl + "/api/shipments";
        SimpleLogger.info("Sending shipping request for order {}", orderId);
        
        try {
            ShipmentResponse response = restTemplate.postForObject(url, request, ShipmentResponse.class);
            if (response != null) {
                SimpleLogger.info("Shipment {} created for order {}", response.getId(), orderId);
            }
            return response;
        } catch (Exception e) {
            SimpleLogger.error("Failed to create shipment for order {}: {}", orderId, e.getMessage());
            return null;
        }
    }
}
