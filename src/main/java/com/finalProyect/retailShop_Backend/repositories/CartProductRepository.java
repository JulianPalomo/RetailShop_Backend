package com.finalProyect.retailShop_Backend.repositories;

import com.finalProyect.retailShop_Backend.entities.CartProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;// Método para obtener todos los productos de un carrito específico
import java.util.Optional;

@Repository
public interface CartProductRepository extends JpaRepository<CartProductEntity, Long> {

    // Método para obtener todos los productos de un carrito específico
    List<CartProductEntity> findByCartId(Long cartId);

    // Método para obtener un producto específico en un carrito por ID de carrito e ID de producto
    Optional<CartProductEntity> findByCartIdAndProductId(Long cartId, Long productId);
}