package com.finalProyect.retailShop_Backend.mappers;
import com.finalProyect.retailShop_Backend.DTO.CartProductDto;
import com.finalProyect.retailShop_Backend.entities.CartProductEntity;
import com.finalProyect.retailShop_Backend.entities.ProductEntity;
import com.finalProyect.retailShop_Backend.entities.SaleEntity;
import org.springframework.stereotype.Component;

@Component
public class CartProductMapper {



    public CartProductEntity toEntity(CartProductDto dto, ProductEntity product, SaleEntity cart) {
        return CartProductEntity.builder()
                .sale(cart)
                .product(product)
                .quantity( dto.getQuantity())
                .unitPrice(dto.getUnitPrice())
                .subTotal(dto.getSubTotal())
                .build();
    }

    public CartProductDto toDto(CartProductEntity entity) {
        return new CartProductDto(
                entity.getId(),                        // ID de la entidad
                entity.getProduct().getSku(),          // SKU desde ProductEntity
                entity.getProduct().getName(),         // Descripción desde ProductEntity
                entity.getQuantity(),                  // Cantidad desde CartProductEntity
                entity.getUnitPrice(),                 // Precio unitario
                entity.getSubTotal()                   // Subtotal calculado
        );
    }

}
