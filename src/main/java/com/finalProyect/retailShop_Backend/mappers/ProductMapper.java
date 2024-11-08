package com.finalProyect.retailShop_Backend.mappers;
import com.finalProyect.retailShop_Backend.DTO.ProductDto;
import com.finalProyect.retailShop_Backend.entities.products.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto toDto(ProductEntity product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setCategoryName(product.getCategory().getName());  // Nombre de la categoría
        dto.setBrandName(product.getBrand().getName());  // Nombre de la marca
        dto.setStockQuantity(product.getStock().getQuantity());  // Stock disponible
        return dto;
    }

    public ProductEntity toEntity(ProductDto dto) {
        ProductEntity product = new ProductEntity();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        // TODO: establecer las referencias a categoría y marca
        return product;
    }
}