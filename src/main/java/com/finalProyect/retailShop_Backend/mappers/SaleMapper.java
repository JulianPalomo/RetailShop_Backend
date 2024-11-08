package com.finalProyect.retailShop_Backend.mappers;

import com.finalProyect.retailShop_Backend.DTO.SaleDto;
import com.finalProyect.retailShop_Backend.entities.SaleEntity;
import com.finalProyect.retailShop_Backend.entities.products.CartProductEntity;
///import com.finalProyect.retailShop_Backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SaleMapper {

    ///private final UserService userService; // Para obtener UserEntity a partir de userId

    // Método para convertir de SaleDto a SaleEntity
    public SaleEntity toEntity(SaleDto saleDto) {
        return SaleEntity.builder()
               /// .user(userService.getUserById(saleDto.getUserId())) // Obtiene UserEntity por ID
                .products(saleDto.getProducts()) // Usamos los productos directamente (asegúrate de que estén mapeados)
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
                saleEntity.getProducts(),
                saleEntity.getTotal(),
                saleEntity.getDate(),
                saleEntity.getPaymentMethod()
        );
    }
}
