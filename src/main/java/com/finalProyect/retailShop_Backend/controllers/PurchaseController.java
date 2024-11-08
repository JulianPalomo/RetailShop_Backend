package com.finalProyect.retailShop_Backend.controllers;

import com.finalProyect.retailShop_Backend.DTO.MessageResponse;
import com.finalProyect.retailShop_Backend.DTO.PurchaseRequestDto;
import com.finalProyect.retailShop_Backend.services.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Finaliza el carrito de compras",
            description = "Cambia el estado del carrito a 'COMPLETADO'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito completado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta (Ejemplo: carrito sin cliente asignado)"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflicto: El recurso ya existe"),
            @ApiResponse(responseCode = "503", description = "Servicio no disponible"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/finalize/{cartId}")
    public void finalizeCart(@PathVariable Long cartId) {
        // Lógica para finalizar el carrito
    }

    @Operation(summary = "Compra de producto",
            description = "Agrega o actualiza la cantidad de un producto en el carrito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto agregado/actualizado correctamente en el carrito"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta (Ejemplo: no hay suficiente stock)"),
            @ApiResponse(responseCode = "404", description = "Producto o carrito no encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflicto: El recurso ya existe"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas"),
            @ApiResponse(responseCode = "503", description = "Servicio no disponible"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @PostMapping("/addProduct")
    public ResponseEntity<MessageResponse> addOrUpdateProductInCart(@RequestBody PurchaseRequestDto purchaseRequestDto) {
        purchaseService.addProductToCart(purchaseRequestDto.getProductId(),
                purchaseRequestDto.getQuantity(),
                purchaseRequestDto.getUserId());
        MessageResponse response = new MessageResponse(200, "Producto procesado en el carrito");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Elimina un producto del carrito",
            description = "Elimina un producto específico del carrito y restaura el stock.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto agregado/actualizado correctamente en el carrito"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta (Ejemplo: no hay suficiente stock)"),
            @ApiResponse(responseCode = "404", description = "Producto o carrito no encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflicto: El recurso ya existe"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas"),
            @ApiResponse(responseCode = "503", description = "Servicio no disponible"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @DeleteMapping("/remove/{productId}/{userId}")
    public ResponseEntity<MessageResponse> removeProductFromCart(@PathVariable Long productId, @PathVariable Long userId) {
        purchaseService.removeProductFromCart(productId, userId);
        MessageResponse response = new MessageResponse(200, "Producto eliminado del carrito");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
