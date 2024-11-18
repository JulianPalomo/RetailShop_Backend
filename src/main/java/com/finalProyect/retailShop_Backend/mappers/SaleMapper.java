package com.finalProyect.retailShop_Backend.mappers;

import com.finalProyect.retailShop_Backend.DTO.CartProductDto;
import com.finalProyect.retailShop_Backend.DTO.SaleDto;
import com.finalProyect.retailShop_Backend.entities.SaleEntity;
import com.finalProyect.retailShop_Backend.entities.persons.UserEntity;
import com.finalProyect.retailShop_Backend.entities.products.CartProductEntity;
///import com.finalProyect.retailShop_Backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SaleMapper {

    // Método para convertir de SaleDto a SaleEntity
    public SaleEntity toEntity(SaleDto saleDto, UserEntity user, List<CartProductEntity> cartProducts) {
        return SaleEntity.builder()
                .user(user) // Asigna el UserEntity
                .products(cartProducts) // Asigna los productos del carrito
                .total(saleDto.getTotal())
                .date(saleDto.getDate())
                .paymentMethod(saleDto.getPaymentMethod())
                .build();
    }

    // Método para convertir de SaleEntity a SaleDto
    public SaleDto toDto(SaleEntity saleEntity) {
        return new SaleDto(
                saleEntity.getId(),
                saleEntity.getUser().getId(),
                saleEntity.getProducts().stream()
                        .map(product -> new CartProductDto(
                                product.getId(),
                                product.getProduct().getName(), // Accede a la descripción desde ProductEntity
                                product.getQuantity().intValue(),
                                product.getUnitPrice(),
                                product.getSubTotal()
                        ))
                        .collect(Collectors.toList()),
                saleEntity.getTotal(),
                saleEntity.getDate(),
                saleEntity.getPaymentMethod()
        );
    }
}

    
