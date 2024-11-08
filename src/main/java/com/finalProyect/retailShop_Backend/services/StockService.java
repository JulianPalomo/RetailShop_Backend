package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.entities.StockEntity;
import com.finalProyect.retailShop_Backend.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    public List<StockEntity> getAllStocks() {
        return stockRepository.findAll();
    }

    public StockEntity getStockByProductId(Long productId) {
        return stockRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Stock not found for product with ID: " + productId));
    }

    // Crear nuevo stock
    public StockEntity createStock(StockEntity stock) {
        return stockRepository.save(stock);
    }

    public StockEntity updateStockByProductId(Long productId, Long newQuantity) {
        StockEntity stock = stockRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Stock not found for product with ID: " + productId));

        stock.setQuantity(newQuantity);
        return stockRepository.save(stock);
    }

}
