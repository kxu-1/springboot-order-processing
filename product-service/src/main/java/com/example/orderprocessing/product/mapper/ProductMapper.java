package com.example.orderprocessing.product.mapper;

import com.example.orderprocessing.product.dto.ProductDto;
import com.example.orderprocessing.product.entity.Product;

public class ProductMapper {
    public static ProductDto mapToProductDto(Product p) {
        if (p == null) return null;
        return new ProductDto(p.getId(), p.getName(), p.getPrice(), p.getQuantity());
    }

    public static Product mapToProduct(ProductDto dto) {
        if (dto == null) return null;
        return new Product(dto.getId(), dto.getName(), dto.getPrice(), dto.getQuantity());
    }
}
