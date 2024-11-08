package com.finalProyect.retailShop_Backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WrongCredentials extends ResponseStatusException {
    public WrongCredentials(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
