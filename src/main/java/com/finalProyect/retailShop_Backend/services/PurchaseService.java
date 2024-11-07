package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.entities.*;
import com.finalProyect.retailShop_Backend.entities.persons.UserEntity;
import com.finalProyect.retailShop_Backend.entities.products.ProductEntity;
import com.finalProyect.retailShop_Backend.entities.products.PurchasedProductEntity;
import com.finalProyect.retailShop_Backend.entities.products.PurchasedProductXCartEntity;
import com.finalProyect.retailShop_Backend.enums.CartStatus;
import com.finalProyect.retailShop_Backend.exceptions.BadRequestException;
import com.finalProyect.retailShop_Backend.exceptions.InsufficientStockException;
import com.finalProyect.retailShop_Backend.exceptions.NotFoundException;
import com.finalProyect.retailShop_Backend.repositories.CartRepository;
import com.finalProyect.retailShop_Backend.repositories.StockRepository;
import com.finalProyect.retailShop_Backend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    public void finalizeCart(Long cartId) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Carrito no encontrado"));

        if (cart.getCustomer() == null) {
            throw new BadRequestException("No se puede facturar sin un cliente asignado");
        }

        // Cambiar el estado del carrito a "COMPLETADO" o el estado que corresponda
        cart.setStatus(CartStatus.COMPLETED);
        cartRepository.save(cart);
    }

    @Transactional
    public void addOrUpdateProductInCart(Long productId, Long quantity, Long userId) {

        CartEntity cart = findOrCreateCartForUser(userId);

        // Verificar stock antes de proceder con la compra
        updateStock(productId, quantity);
        addOrUpdateProductInCart(cart, productId, quantity);

        cartRepository.save(cart);
    }

    @Transactional
    public void removeProductFromCart(Long productId, Long userId) {
        CartEntity cart = findOrCreateCartForUser(userId);

        // Buscar el producto en el carrito
        PurchasedProductXCartEntity productInCart = findProductInCart(cart, productId);
        if (productInCart == null) {
            throw new NotFoundException("Producto no encontrado en el carrito");
        }

        // Eliminar el producto del carrito
        cart.getPurchasedProducts().remove(productInCart);

        // Sumar el stock del producto eliminado
        updateStockForRemoval(productId, productInCart.getPurchasedProduct().getQuantity());

        // Guardar los cambios en el carrito
        cartRepository.save(cart);
    }

    private void updateStockForRemoval(Long productId, Long quantity) {
        StockEntity stock = stockRepository.findByProductId(productId);
        if (stock == null) {
            throw new NotFoundException("Producto no encontrado en el inventario");
        }
        stock.setQuantity(stock.getQuantity() + quantity);
        stockRepository.save(stock);
    }

    private CartEntity findOrCreateCartForUser(Long userId) {
        return cartRepository.findByUserIdAndStatus(userId, CartStatus.PENDING)
                .orElseGet(() -> createNewCart(userRepository.findById(userId)
                        .orElseThrow(() -> new NotFoundException("Usuario no encontrado"))));
    }

    private CartEntity createNewCart(UserEntity user) {
        CartEntity cart = new CartEntity();
        cart.setUser(user);  // Asocia el carrito al usuario
        cart.setStatus(CartStatus.PENDING);
        cart.setPurchasedProducts(new ArrayList<>());
        cart.setTotal(BigDecimal.ZERO);
        return cartRepository.save(cart);
    }

    private void updateStock(Long productId, Long quantity) {
        StockEntity stock = stockRepository.findByProductId(productId);
        if (stock == null) {
            throw new NotFoundException("Producto no encontrado en el inventario");
        }
        if (stock.getQuantity() < quantity) {
            throw new InsufficientStockException("No hay suficiente stock disponible");
        }
        stock.setQuantity(stock.getQuantity() - quantity);
        stockRepository.save(stock);
    }

    private void addOrUpdateProductInCart(CartEntity cart, Long productId, Long quantity) {
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

    private void updateProductQuantity(PurchasedProductXCartEntity purchasedProductXCart, Long quantity) {
        PurchasedProductEntity purchasedProduct = purchasedProductXCart.getPurchasedProduct();
        purchasedProduct.setQuantity(purchasedProduct.getQuantity() + quantity);
        purchasedProduct.setSubTotal(purchasedProduct.getUnitPrice().multiply(BigDecimal.valueOf(purchasedProduct.getQuantity())));
    }

    private void addNewProductToCart(CartEntity cart, Long productId, Long quantity) {
        StockEntity stock = stockRepository.findByProductId(productId);
        if (stock == null) {
            throw new NotFoundException("Producto no encontrado en el inventario");
        }

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

