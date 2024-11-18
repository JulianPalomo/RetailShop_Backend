package com.finalProyect.retailShop_Backend.controllers;

import com.finalProyect.retailShop_Backend.DTO.LoginRequestDto;
import com.finalProyect.retailShop_Backend.DTO.UserDto;
import com.finalProyect.retailShop_Backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        UserDto userDto = userService.authenticate(loginRequest.getDni(), loginRequest.getPassword());
        if (userDto != null) {
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}