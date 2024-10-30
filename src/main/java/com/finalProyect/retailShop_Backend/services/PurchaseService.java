package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.entity.*;
import com.finalProyect.retailShop_Backend.enums.CartStatus;
import com.finalProyect.retailShop_Backend.repositories.CartRepository;
import com.finalProyect.retailShop_Backend.repositories.StockRepository;
import com.finalProyect.retailShop_Backend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class PurchaseService {

    private final StockRepository stockRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public PurchaseService(StockRepository stockRepository, CartRepository cartRepository, UserRepository userRepository) {
        this.stockRepository = stockRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void purchaseProduct(Long productId, int quantity, Long userId) {
        CartEntity cart = findOrCreateCartForUser(userId);

        updateStock(productId, quantity);
        addOrUpdateProductInCart(cart, productId, quantity);

        cartRepository.save(cart);
    }

    private CartEntity findOrCreateCartForUser(Long userId) {
        return cartRepository.findByUserIdAndStatus(userId, CartStatus.PENDING)
                .orElseGet(() -> createNewCart(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"))));
    }

    private CartEntity createNewCart(UserEntity userId) {
        CartEntity cart = new CartEntity();
        cart.setUser(userId);  // Asocia el carrito al usuario
        cart.setCartStatus(CartStatus.PENDING);
        cart.setPurchasedProducts(new ArrayList<>());
        cart.setTotal(BigDecimal.ZERO);
        return cartRepository.save(cart);
    }

    private void updateStock(Long productId, int quantity) {
        StockEntity stock = stockRepository.findByProductId(productId);
        if (stock == null || stock.getStock() < quantity) {
            throw new RuntimeException("No hay suficiente stock disponible");
        }
        stock.setStock(stock.getStock() - quantity);
        stockRepository.save(stock);
    }

    private void addOrUpdateProductInCart(CartEntity cart, Long productId, int quantity) {
        PurchasedProductXCartEntity existingProductInCart = findProductInCart(cart, productId);

        if (existingProductInCart != null) {
            updateProductQuantity(existingProductInCart, quantity);
        } else {
            addNewProductToCart(cart, productId, quantity);
        }
    }

    private PurchasedProductXCartEntity findProductInCart(CartEntity cart, Long productId) {
        return cart.getPurchasedProducts().stream()
                .filter(p -> p.getPurchasedProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    private void updateProductQuantity(PurchasedProductXCartEntity purchasedProductXCart, int quantity) {
        PurchasedProductEntity purchasedProduct = purchasedProductXCart.getPurchasedProduct();
        purchasedProduct.setQuantity(purchasedProduct.getQuantity() + quantity);
        purchasedProduct.setSubTotal(purchasedProduct.getUnitPrice().multiply(BigDecimal.valueOf(purchasedProduct.getQuantity())));
    }

    private void addNewProductToCart(CartEntity cart, Long productId, int quantity) {
        StockEntity stock = stockRepository.findByProductId(productId);

        PurchasedProductEntity purchasedProduct = new PurchasedProductEntity();
        purchasedProduct.setId(productId);
        purchasedProduct.setQuantity(quantity);
        purchasedProduct.setUnitPrice(stock.getProduct().getPrice());
        purchasedProduct.setSubTotal(purchasedProduct.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));

        PurchasedProductXCartEntity purchasedProductXCart = new PurchasedProductXCartEntity();
        purchasedProductXCart.setCart(cart);
        purchasedProductXCart.setPurchasedProduct(purchasedProduct);

        cart.getPurchasedProducts().add(purchasedProductXCart);
    }
}
