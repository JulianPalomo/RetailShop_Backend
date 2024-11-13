package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.entities.CartEntity;
import com.finalProyect.retailShop_Backend.entities.products.CartProductEntity;
import com.finalProyect.retailShop_Backend.entities.products.ProductEntity;
import com.finalProyect.retailShop_Backend.enums.CartStatus;
import com.finalProyect.retailShop_Backend.repositories.CartProductRepository;
import com.finalProyect.retailShop_Backend.repositories.CartRepository;
import com.finalProyect.retailShop_Backend.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartProductRepository cartProductRepository;

    // Método para agregar un producto al carrito
    public CartEntity addProductToCart(Long cartId, Long productId, Long quantity) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock for product: " + product.getName());
        }

        // Descontar stock temporalmente
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        // Calcular subtotal
        BigDecimal unitPrice = product.getPrice();
        BigDecimal subTotal = unitPrice.multiply(BigDecimal.valueOf(quantity));

        // Crear CartProductEntity y agregar al carrito
        CartProductEntity cartProduct = new CartProductEntity();
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setQuantity(quantity);
        cartProduct.setUnitPrice(unitPrice);
        cartProduct.setSubTotal(subTotal);

        cartProductRepository.save(cartProduct);

        // Actualizar total del carrito
        cart.setTotal(cart.getTotal().add(subTotal));
        return cartRepository.save(cart);
    }

    // Método para eliminar un producto del carrito
    public CartEntity removeProductFromCart(Long cartId, Long cartProductId) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartProductEntity cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(() -> new RuntimeException("Product in cart not found"));

        // Restaurar stock del producto
        ProductEntity product = cartProduct.getProduct();
        product.setStock(product.getStock()+ cartProduct.getQuantity());
        productRepository.save(product);

        // Restar el subtotal del producto del total del carrito
        cart.setTotal(cart.getTotal().subtract(cartProduct.getSubTotal()));

        cartProductRepository.delete(cartProduct);

        return cartRepository.save(cart);
    }

    // Método para finalizar el carrito
    public CartEntity finalizeCart(Long cartId) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Cambiar estado a COMPLETADO
        cart.setStatus(CartStatus.COMPLETED);
        return cartRepository.save(cart);
    }

    // Método para cancelar el carrito
    public CartEntity cancelCart(Long cartId) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getStatus() != CartStatus.PENDING) {
            throw new RuntimeException("Only pending carts can be canceled");
        }

        // Restaurar stock para cada producto en el carrito
        for (CartProductEntity cartProduct : cart.getCartProducts()) {
            ProductEntity product = cartProduct.getProduct();
            product.setStock(product.getStock() + cartProduct.getQuantity());
            productRepository.save(product);
        }

        // Cambiar estado a CANCELADO
        cart.setStatus(CartStatus.CANCELLED);
        return cartRepository.save(cart);
    }
}