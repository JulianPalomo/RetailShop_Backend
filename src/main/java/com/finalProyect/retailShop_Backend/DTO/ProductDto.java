package com.finalProyect.retailShop_Backend.DTO;

import com.finalProyect.retailShop_Backend.entities.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductDto{

    private Long id;
    private String sku;
    private String description;
    private BigDecimal unitPrice;
    private CategoryEntity category;
    private Long stock;
    private Long minimumStock;

}