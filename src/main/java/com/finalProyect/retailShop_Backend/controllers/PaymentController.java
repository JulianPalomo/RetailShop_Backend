package com.finalProyect.retailShop_Backend.controllers;

import com.finalProyect.retailShop_Backend.DTO.CardDto;
import com.finalProyect.retailShop_Backend.entities.CardEntity;
import com.finalProyect.retailShop_Backend.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final CardService cardService;

    public PaymentController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyPayment(@RequestBody CardDto cardDto) {
        boolean isValid = cardService.verifyCard(cardDto);
        return isValid ? ResponseEntity.ok(true) : ResponseEntity.badRequest().body(false);
    }
}