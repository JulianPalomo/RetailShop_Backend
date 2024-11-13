package com.finalProyect.retailShop_Backend.DTO;



import com.finalProyect.retailShop_Backend.entities.products.CartProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDto {
    private Long id;  // ID de la venta
    private Long userId;  // ID del usuario que realiza la venta
    private Long clientId;  // Número de DNI o ID del cliente
    private List<CartProductDto> products;  // Lista de productos en la venta (usando DTO en lugar de entidad)
    private BigDecimal total;  // Total de la venta
    private LocalDate date;  // Fecha de la venta
    private String paymentMethod;  // Método de pago
}
