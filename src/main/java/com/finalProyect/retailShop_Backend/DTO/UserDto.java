package com.finalProyect.retailShop_Backend.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {
    private Long id;
    private String name;
    private String dni;
    private String email;
    private String password;
    @JsonProperty("isAdmin") // Forzamos el nombre del campo en el JSON
    private boolean isAdmin;
    @JsonProperty("isActive")
    private boolean isActive;
}