package com.finalProyect.retailShop_Backend.repositories;

import com.finalProyect.retailShop_Backend.entities.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, String> {
    // Método para encontrar una tarjeta por sus detalles
    boolean existsByCardNumberAndCardExpiryAndCardCvv(String cardNumber, String cardExpiry, String cardCvv);
}