package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.entities.*;

import com.finalProyect.retailShop_Backend.entities.products.CartProductEntity;
import com.finalProyect.retailShop_Backend.entities.products.ProductEntity;
import com.finalProyect.retailShop_Backend.enums.CartStatus;
import com.finalProyect.retailShop_Backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class PurchaseService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartProductRepository cartProductRepository;

    // Método para agregar un producto al carrito
    public CartEntity addProductToCart(Long cartId, Long productId, Long quantity) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        checkAndDecreaseStock(product, quantity);

        BigDecimal unitPrice = product.getPrice();
        BigDecimal subTotal = unitPrice.multiply(BigDecimal.valueOf(quantity));

        CartProductEntity cartProduct = new CartProductEntity();
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setQuantity(quantity);
        cartProduct.setUnitPrice(unitPrice);
        cartProduct.setSubTotal(subTotal);
//
        cartProductRepository.save(cartProduct);

        cart.setTotal(cart.getTotal().add(subTotal));
        return cartRepository.save(cart);
    }

    // Método para eliminar un producto del carrito
    public CartEntity removeProductFromCart(Long cartId, Long cartProductId) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        CartProductEntity cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(() -> new RuntimeException("Producto en carrito no encontrado"));

        restoreStock(cartProduct.getProduct(), cartProduct.getQuantity());

        cart.setTotal(cart.getTotal().subtract(cartProduct.getSubTotal()));
        cartProductRepository.delete(cartProduct);

        return cartRepository.save(cart);
    }

    // Método para finalizar el carrito
    public CartEntity finalizeCart(Long cartId) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        if (cart.getStatus() != CartStatus.PENDING) {
            throw new RuntimeException("Solo los carritos pendientes se pueden finalizar");
        }

        cart.setStatus(CartStatus.COMPLETED);
        return cartRepository.save(cart);
    }

    // Método para cancelar el carrito
    public CartEntity cancelCart(Long cartId) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        if (cart.getStatus() != CartStatus.PENDING) {
            throw new RuntimeException("Solo los carritos pendientes se pueden cancelar");
        }

        for (CartProductEntity cartProduct : cart.getCartProducts()) {
            restoreStock(cartProduct.getProduct(), cartProduct.getQuantity());
        }

        cart.setStatus(CartStatus.CANCELLED);
        return cartRepository.save(cart);
    }

    private void checkAndDecreaseStock(ProductEntity product, Long quantity) {
        if (product.getStock().getQuantity() < quantity) {
            throw new RuntimeException("Stock insuficiente para el producto: " + product.getName());
        }
        product.getStock().setQuantity(product.getStock().getQuantity() - quantity);
        productRepository.save(product);
    }

    private void restoreStock(ProductEntity product, Long quantity) {
        product.getStock().setQuantity(product.getStock().getQuantity() + quantity);
        productRepository.save(product);
    }
}