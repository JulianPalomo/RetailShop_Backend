package com.finalProyect.retailShop_Backend.controllers;

import com.finalProyect.retailShop_Backend.DTO.SaleDto;
import com.finalProyect.retailShop_Backend.entities.CartEntity;
import com.finalProyect.retailShop_Backend.services.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    // Confirma una venta y descuenta el stock
    @PostMapping("/confirm")
    public ResponseEntity<SaleDto> confirmarVenta(@RequestBody SaleDto saleDto) {
        SaleDto ventaConfirmada = saleService.confirmarVenta(saleDto);
        return new ResponseEntity<>(ventaConfirmada, HttpStatus.CREATED);
    }

    // Obtener todas las ventas
    @GetMapping
    public ResponseEntity<List<SaleDto>> obtenerVentas() {
        List<SaleDto> ventas = saleService.obtenerTodasLasVentas();
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }

    // Obtener una venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<SaleDto> obtenerVentaPorId(@PathVariable Long id) {
        SaleDto venta = saleService.obtenerVentaPorId(id);
        return venta != null ? ResponseEntity.ok(venta) : ResponseEntity.notFound().build();
    }

    // Eliminar una venta por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        boolean eliminado = saleService.eliminarVentaPorId(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
