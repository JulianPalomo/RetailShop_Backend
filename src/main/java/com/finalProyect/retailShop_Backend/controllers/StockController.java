package com.finalProyect.retailShop_Backend.controllers;

import com.finalProyect.retailShop_Backend.entities.StockEntity;
import com.finalProyect.retailShop_Backend.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")

public class StockController {
    //TODO crud de stock basado en id producto

    @Autowired
    private StockService stockService;

    // Obtener todos los stocks
    @GetMapping
    public List<StockEntity> getAllStocks() {
        return stockService.getAllStocks();
    }

    // Obtener stock por id producto
    @GetMapping("/{id}")
    public StockEntity getStockById(@PathVariable Long idProduct) {
        return stockService.getStockByProductId(idProduct);
    }

    // Crear nuevo stock cuando cargas un nuevo producto
    @PostMapping
    public StockEntity createStock(@RequestBody StockEntity stock) {
        return stockService.createStock(stock);
    }

    // Actualizar cantidad de stock por id de producto
    @PutMapping("/{id}")
    public StockEntity updateStock(@PathVariable Long idProduct, @RequestParam Long quantity) {
        return stockService.updateStockByProductId(idProduct, quantity);
    }



}
