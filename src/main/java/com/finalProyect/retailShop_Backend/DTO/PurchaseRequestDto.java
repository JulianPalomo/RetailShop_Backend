package com.finalProyect.retailShop_Backend.DTO;


import lombok.Data;

@Data
public class PurchaseRequestDto {
    private Long userId;
    private Long productId;
    private Long quantity;
}