package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.DTO.CardDto;
import com.finalProyect.retailShop_Backend.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    // Verifica si los detalles de la tarjeta son válidos
    public boolean verifyCard(CardDto cardDto) {
        // Llamamos al repositorio para verificar si existe una tarjeta con estos detalles
        return cardRepository.existsByCardNumberAndCardExpiryAndCardCvv(
                cardDto.getCardNumber(),
                cardDto.getCardExpiry(),
                cardDto.getCardCvv()
        );
    }
}