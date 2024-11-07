package com.finalProyect.retailShop_Backend.controllers;

import com.finalProyect.retailShop_Backend.DTO.MessageResponse;
import com.finalProyect.retailShop_Backend.DTO.PurchaseRequestDto;
import com.finalProyect.retailShop_Backend.services.CartService;
import com.finalProyect.retailShop_Backend.services.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    // POST: Agregar o actualizar producto en el carrito
    @PostMapping("/addOrUpdate")
    public ResponseEntity<MessageResponse> addOrUpdateProductInCart(@RequestBody PurchaseRequestDto purchaseRequestDto) {
        purchaseService.addOrUpdateProductInCart(purchaseRequestDto.getProductId(),
                purchaseRequestDto.getQuantity(),
                purchaseRequestDto.getUserId());
        MessageResponse response = new MessageResponse(200, "Producto procesado en el carrito");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // DELETE: Eliminar un producto del carrito
    @DeleteMapping("/remove/{productId}/{userId}")
    public ResponseEntity<MessageResponse> removeProductFromCart(@PathVariable Long productId, @PathVariable Long userId) {
        purchaseService.removeProductFromCart(productId, userId);
        MessageResponse response = new MessageResponse(200, "Producto eliminado del carrito");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
