package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.entities.CartEntity;
import com.finalProyect.retailShop_Backend.enums.CartStatus;
import com.finalProyect.retailShop_Backend.repositories.CartRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Optional<CartEntity> getCartForUser(Long userId) {
        return cartRepository.findByUserIdAndStatus(userId, CartStatus.PENDING);
    }
}