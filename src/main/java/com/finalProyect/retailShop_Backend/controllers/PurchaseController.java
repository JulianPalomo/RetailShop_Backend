package com.finalProyect.retailShop_Backend.controllers;

import com.finalProyect.retailShop_Backend.DTO.PurchaseRequestDto;
import com.finalProyect.retailShop_Backend.entity.CartEntity;
import com.finalProyect.retailShop_Backend.services.CartService;
import com.finalProyect.retailShop_Backend.services.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final CartService cartService; // Inyección del nuevo servicio

    @Autowired
    public PurchaseController(PurchaseService purchaseService, CartService cartService) {
        this.purchaseService = purchaseService;
        this.cartService = cartService; // Asignación del servicio
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseProduct(@RequestBody PurchaseRequestDto purchaseRequestDto) {
        try {
            purchaseService.purchaseProduct(purchaseRequestDto.getProductId(), purchaseRequestDto.getQuantity(), purchaseRequestDto.getUserId());
            return ResponseEntity.status(HttpStatus.CREATED).body("Producto agregado al carrito exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{userId}/cart")
    public ResponseEntity<CartEntity> getCart(@PathVariable Long userId) {
        CartEntity cart = cartService.getCartForUser(userId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado para el usuario"));
        return ResponseEntity.ok(cart);
    }
}
