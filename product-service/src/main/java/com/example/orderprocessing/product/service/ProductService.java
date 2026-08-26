package com.example.orderprocessing.product.service;

import com.example.orderprocessing.product.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Long id);
    ProductDto updateProduct(Long id, ProductDto productDto);
    void deleteProduct(Long id);
    void reserveInventory(Long productId, Integer quantity, Long orderId);
}
