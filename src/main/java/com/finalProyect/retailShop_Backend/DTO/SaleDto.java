package com.finalProyect.retailShop_Backend.DTO;


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

    private Long id;
    private Long employeeId;
    private Long clientId; //dni de comprador
    private List<CartProductDto> products;
    private BigDecimal total;
    private String date;
    private String paymentMethod;
}