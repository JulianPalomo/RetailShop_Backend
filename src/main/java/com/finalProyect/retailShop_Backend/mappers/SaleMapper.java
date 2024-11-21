package com.finalProyect.retailShop_Backend.mappers;

import com.finalProyect.retailShop_Backend.DTO.CartProductDto;
import com.finalProyect.retailShop_Backend.DTO.SaleDto;
import com.finalProyect.retailShop_Backend.entities.SaleEntity;
import com.finalProyect.retailShop_Backend.entities.persons.UserEntity;
import com.finalProyect.retailShop_Backend.entities.CartProductEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class SaleMapper {

    private final CartProductMapper cartProductMapper;

    public SaleMapper(CartProductMapper cartProductMapper) {
        this.cartProductMapper = cartProductMapper;
    }

    public SaleDto toDto(SaleEntity sale) {
        SaleDto dto = new SaleDto();
        dto.setId(sale.getId());
        dto.setEmployeedId(sale.getUser().getId());
        dto.setProducts(sale.getProducts().stream()
                .map(cartProductMapper::toDto)
                .collect(Collectors.toList()));
        dto.setTotal(sale.getTotal());
        dto.setDate(sale.getDate());
        dto.setPaymentMethod(sale.getPaymentMethod());
        return dto;
    }

    public SaleEntity toEntity(SaleDto dto, UserEntity user, List<CartProductEntity> cartProducts) {
        SaleEntity sale = new SaleEntity();
        sale.setId(dto.getId());
        sale.setUser(user);
        sale.setClientId(dto.getClientId());
        sale.setProducts(cartProducts);
        sale.setTotal(dto.getTotal());
        sale.setDate(dto.getDate());
        sale.setPaymentMethod(dto.getPaymentMethod());
        return sale;
    }

    public List<CartProductDto> toDtoList(List<CartProductEntity> entityList) {
        return entityList.stream()
                .map(cartProductMapper::toDto)
                .collect(Collectors.toList());
    }
}