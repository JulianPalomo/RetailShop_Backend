package com.finalProyect.retailShop_Backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ServiceDownException extends ResponseStatusException {
    public ServiceDownException(String message) {
        super(HttpStatus.SERVICE_UNAVAILABLE, message);
    }
}
