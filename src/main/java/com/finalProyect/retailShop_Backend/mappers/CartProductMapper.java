package com.finalProyect.retailShop_Backend.mappers;

import com.finalProyect.retailShop_Backend.DTO.CartProductDto;
import com.finalProyect.retailShop_Backend.entities.CartEntity;
import com.finalProyect.retailShop_Backend.entities.products.CartProductEntity;
import com.finalProyect.retailShop_Backend.entities.products.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class CartProductMapper {

    public CartProductDto toDto(CartProductEntity entity) {
        return new CartProductDto(
                entity.getProduct().getId(), // Suponiendo que SKU corresponde a Product ID
                entity.getProduct().getName(), // Suponiendo que description corresponde al nombre del producto
                entity.getQuantity().intValue(),
                entity.getUnitPrice(),
                entity.getSubTotal()
        );
    }

    public CartProductEntity toEntity(CartProductDto dto, ProductEntity product, CartEntity cart) {
        return CartProductEntity.builder()
                .cart(cart)
                .product(product)
                .quantity((long) dto.getQuantity())
                .unitPrice(dto.getUnitPrice())
                .subTotal(dto.getSubTotal())
                .build();
    }
}