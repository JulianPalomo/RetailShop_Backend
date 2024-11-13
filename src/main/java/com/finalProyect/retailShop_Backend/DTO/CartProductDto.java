package com.finalProyect.retailShop_Backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartProductDto {
    private Long sku;  // SKU del producto
    private String description;  // Descripción del producto
    private int quantity;  // Cantidad de unidades del producto
    private BigDecimal unitPrice;  // Precio por unidad
    private BigDecimal subTotal;  // Subtotal (quantity * unitPrice)
}