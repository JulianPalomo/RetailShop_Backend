package com.finalProyect.retailShop_Backend.mappers;
import com.finalProyect.retailShop_Backend.DTO.ProductDto;
import com.finalProyect.retailShop_Backend.entities.products.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto toDto(ProductEntity product) {
        ProductDto dto = new ProductDto();
        dto.setSku(product.getId());
        dto.setDescription(product.getName());
        dto.setUnitPrice(product.getPrice());
        dto.setCategory(product.getCategory());
        dto.setStock(product.getStock());
        return dto;
    }

    public ProductEntity toEntity(ProductDto dto) {
        ProductEntity product = new ProductEntity();
        product.setId(dto.getSku());
        product.setName(dto.getDescription());
        product.setPrice(dto.getUnitPrice());
        product.setCategory(dto.getCategory());
        return product;
    }
}