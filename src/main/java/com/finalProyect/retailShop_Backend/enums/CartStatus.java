package com.finalProyect.retailShop_Backend.enums;

public enum CartStatus {
    PENDING,      // Carrito en estado de edición
    COMPLETED,    // Carrito con compra finalizada
    CANCELLED,    // Carrito cancelado antes de la compra
    PROCESSING    // Carrito en proceso de pago/envío
}