package com.finalProyect.retailShop_Backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartProductDto {

    private Long id;
    private String name;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

}
