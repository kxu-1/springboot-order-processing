package com.example.orderprocessing.product.service.impl;

import com.example.orderprocessing.product.dto.InventoryReservedEvent;
import com.example.orderprocessing.product.dto.ProductDto;
import com.example.orderprocessing.product.entity.Product;
import com.example.orderprocessing.product.exception.ResourceNotFoundException;
import com.example.orderprocessing.product.mapper.ProductMapper;
import com.example.orderprocessing.product.producer.InventoryEventProducer;
import com.example.orderprocessing.product.service.ProductService;
import com.example.orderprocessing.product.util.SimpleLogger;
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
public class ProductServiceImpl implements ProductService {

    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);
    private final InventoryEventProducer eventProducer;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        SimpleLogger.info("Creating product with name: {}", productDto.getName());
        long id = idGenerator.incrementAndGet();
        Product product = ProductMapper.mapToProduct(productDto);
        product.setId(id);
        products.put(id, product);
        return ProductMapper.mapToProductDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        SimpleLogger.info("Fetching all products");
        return products.values().stream()
                .map(ProductMapper::mapToProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long id) {
        SimpleLogger.info("Fetching product with ID: {}", id);
        Product product = products.get(id);
        if (product == null) {
            throw new ResourceNotFoundException("Product with ID " + id + " does not exist");
        }
        return ProductMapper.mapToProductDto(product);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        SimpleLogger.info("Updating product with ID: {}", id);
        Product product = products.get(id);
        if (product == null) {
            throw new ResourceNotFoundException("Product with ID " + id + " does not exist");
        }
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        return ProductMapper.mapToProductDto(product);
    }

    @Override
    public void deleteProduct(Long id) {
        SimpleLogger.info("Deleting product with ID: {}", id);
        if (!products.containsKey(id)) {
            throw new ResourceNotFoundException("Product with ID " + id + " does not exist");
        }
        products.remove(id);
    }

    @Override
    public void reserveInventory(Long productId, Integer quantity, Long orderId) {
        Product product = products.get(productId);
        if (product == null) {
            SimpleLogger.error("Product with ID {} not found for order {}", productId, orderId);
            return;
        }

        int oldQuantity = product.getQuantity();
        if (oldQuantity < quantity) {
            SimpleLogger.error("Insufficient inventory for product {} (requested: {}, available: {}) for order {}",
                    productId, quantity, oldQuantity, orderId);
            return;
        }

        int newQuantity = oldQuantity - quantity;
        product.setQuantity(newQuantity);
        SimpleLogger.info("Product {} inventory changed from {} to {}", productId, oldQuantity, newQuantity);

        InventoryReservedEvent reservedEvent = new InventoryReservedEvent(
                UUID.randomUUID().toString(),
                "INVENTORY_RESERVED",
                orderId,
                productId,
                quantity,
                newQuantity,
                LocalDateTime.now().toString()
        );

        eventProducer.publishInventoryReserved(reservedEvent);
    }
}
