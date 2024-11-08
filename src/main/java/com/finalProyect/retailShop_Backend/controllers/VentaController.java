package com.finalProyect.retailShop_Backend.controllers;


import com.finalProyect.retailShop_Backend.DTO.VentaDto;
import com.finalProyect.retailShop_Backend.entities.CartEntity;
import com.finalProyect.retailShop_Backend.entities.VentaEntity;
import com.finalProyect.retailShop_Backend.services.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    // Recibir un carrito y confirmar la venta
    @PostMapping("/confirmar")
    public ResponseEntity<VentaDto> confirmarVenta(@RequestBody CartEntity carrito) {
        VentaDto ventaConfirmada = ventaService.confirmarVenta(carrito);
        return new ResponseEntity<>(ventaConfirmada, HttpStatus.CREATED);
    }
}
