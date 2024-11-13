package com.finalProyect.retailShop_Backend.mappers;

import com.finalProyect.retailShop_Backend.DTO.CartProductDto;
import com.finalProyect.retailShop_Backend.DTO.ProductDto;
import com.finalProyect.retailShop_Backend.DTO.SaleDto;
import com.finalProyect.retailShop_Backend.entities.SaleEntity;
import com.finalProyect.retailShop_Backend.entities.products.ProductEntity;
import com.finalProyect.retailShop_Backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SaleMapper {

    private final UserService userService;

    public SaleEntity toEntity(SaleDto saleDto) {
        return SaleEntity.builder()
                //.user(userService.getUserById(saleDto.getUserId())) // Obtiene el usuario usando su ID
                .products(saleDto.getProducts().stream() // Convierte la lista de ProductDto a ProductEntity
                        .map(this::convertToProductEntity) // Llama al método que creamos
                        .collect(Collectors.toList())) // Recoge los elementos convertidos en una lista
                .total(saleDto.getTotal())
                .date(saleDto.getDate())
                .paymentMethod(saleDto.getPaymentMethod())
                .build();
    }

    private ProductEntity convertToProductEntity(ProductDto productDto) {
        return ProductEntity.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .stock(productDto.getStockQuantity())
                .build();
    }

    public SaleDto toDto(SaleEntity saleEntity) {
        return new SaleDto(
        );
    }
}