package com.finalProyect.retailShop_Backend.controllers;


import com.finalProyect.retailShop_Backend.DTO.SaleDto;
import com.finalProyect.retailShop_Backend.mappers.SaleMapper;
import com.finalProyect.retailShop_Backend.repositories.SaleRepository;
import com.finalProyect.retailShop_Backend.services.SaleService;
import com.itextpdf.text.DocumentException;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class SaleController {

    @Autowired
    private final SaleService saleService;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleMapper saleMapper;

    @PostMapping("/confirm")
    public ResponseEntity<SaleDto> confirmarVenta(@RequestBody SaleDto saleDto) {
        try {
            SaleDto ventaConfirmada = saleService.confirmarVenta(saleDto);
            return new ResponseEntity<>(ventaConfirmada, HttpStatus.CREATED);
        } catch (Exception e) {
            return (ResponseEntity<SaleDto>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /*

    @GetMapping
    public ResponseEntity<?> obtenerVentas() {
        try {
            List<SaleDto> ventas = saleService.obtenerTodasLasVentas();
            if (ventas.isEmpty()) {
                return new ResponseEntity<>("No se encontraron ventas", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ventas, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener las ventas: " + e.getMessage());
        }
    }*/

   @GetMapping
   public ResponseEntity<?> obtenerVentas(
//           @RequestParam(required = false) Optional<Long> id
   )
   {
       List<SaleDto> ventas = saleService.obtenerVentas();

       if (ventas.isEmpty()) {
           return new ResponseEntity<>("No se encontraron ventas", HttpStatus.NOT_FOUND);
       }
       return new ResponseEntity<>(ventas, HttpStatus.OK);
   }



    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerVentaPorId(@PathVariable Long id) {
        try {
            List<SaleDto> venta = saleService.obtenerVentaPorId(id);
            if (venta == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Venta no encontrada con el ID: " + id);
            }
            return ResponseEntity.ok(venta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener la venta: " + e.getMessage());
        }
    }

    @GetMapping("/venta/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id)
    {
        SaleDto sale = saleService.obtenerVentaPorIdVenta(id);

        if( sale != null){
            return ResponseEntity.ok(sale);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Venta no encontrada");
        }
    }


    // Endpoint para generar la factura en PDF
    @GetMapping("/download/{saleId}")
    public ResponseEntity<byte[]> generateInvoice(@PathVariable Long saleId) throws DocumentException, IOException {
        // Lógica para obtener la venta (sale) por saleId
        SaleDto sale = saleService.obtenerVentaPorIdVenta(saleId);
        return saleService.generateSalePdf(sale);
    }

}