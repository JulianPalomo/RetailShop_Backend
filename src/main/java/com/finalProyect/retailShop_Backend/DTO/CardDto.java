package com.finalProyect.retailShop_Backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {
    private String cardNumber;
    private String cardExpiry;
    private String cardCvv;

}
