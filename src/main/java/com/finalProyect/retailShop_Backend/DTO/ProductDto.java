package com.finalProyect.retailShop_Backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto{

    private Long id;
    private String name;
    private BigDecimal price;
    private boolean isActive;
    private String categoryName;
    private Long stockQuantity;
    private String brandName;
}