package com.finalProyect.retailShop_Backend.DTO;



import com.finalProyect.retailShop_Backend.entities.products.CartProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


//TODO : AGREGAR CLIENTID(NUMERO DE DNI);
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDto {
    private Long id;
    private Long userId;  // ID del usuario que realiza la venta
    private List<CartProductEntity> products;  // Lista de productos en la venta
    private BigDecimal total;  // Total de la venta
    private LocalDate date;  // Fecha de la venta
    private String paymentMethod;  // Método de pago
}
