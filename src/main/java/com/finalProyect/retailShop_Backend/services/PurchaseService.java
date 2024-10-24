package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.entity.PurchasedProductXCartEntity;
import com.finalProyect.retailShop_Backend.entity.StockEntity;
import com.finalProyect.retailShop_Backend.repositories.CartRepository;
import com.finalProyect.retailShop_Backend.repositories.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseService {

    private final StockRepository productStockRepository;
    private final CartRepository cartRepository;

    public PurchaseService(StockRepository productStockRepository, CartRepository cartRepository) {
        this.productStockRepository = productStockRepository;
        this.cartRepository = cartRepository;
    }

    @Transactional  // Esta anotación asegura que todo lo que ocurra dentro de este método sea una única transacción
    public void purchaseProduct(Long productId, int quantity, CartEntity cart) {
        // Paso 1: Restar del stock
        StockEntity stock = productStockRepository.findByProductId(productId);
        if (stock.getStock() < quantity) {
            throw new RuntimeException("No hay suficiente stock disponible");
        }
        stock.setStock(stock.getStock() - quantity);
        productStockRepository.save(stock);

        // Paso 2: Registrar el producto en el carrito
        PurchasedProductXCartEntity purchasedProduct = new PurchasedProductXCartEntity();
        purchasedProduct.setOrder(cart);
        purchasedProduct.setQuantity(quantity);
        cart.getProducts().add(purchasedProduct);

        // Registrar el carrito con los productos
        cartRepository.save(cart);
    }
}
