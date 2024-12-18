package com.finalProyect.retailShop_Backend.mappers;
import com.finalProyect.retailShop_Backend.DTO.ProductDto;
import com.finalProyect.retailShop_Backend.entities.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto toDto(ProductEntity product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setSku(product.getSku());
        dto.setDescription(product.getName());
        dto.setUnitPrice(product.getPrice());
        dto.setMinimumStock(product.getMinimumStock());
        dto.setCategory(product.getCategory());
        dto.setStock(product.getStock());
        return dto;
    }

    public ProductEntity toEntity(ProductDto dto) {
        ProductEntity product = new ProductEntity();
        product.setId(dto.getId());
        product.setSku(dto.getSku());
        product.setName(dto.getDescription());
        product.setPrice(dto.getUnitPrice());
        product.setStock(dto.getStock());
        product.setMinimumStock(dto.getMinimumStock());
        product.setCategory(dto.getCategory());
        return product;
    }

}