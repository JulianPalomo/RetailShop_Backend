package com.finalProyect.retailShop_Backend.entities.persons;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "users")
@Data  // Genera automáticamente getters, setters, equals, hashcode y toString
@NoArgsConstructor  // Genera un constructor sin argumentos
@AllArgsConstructor  // Genera un constructor con todos los argumentos
@SuperBuilder  // Facilita la creación de objetos con el patrón builder
public class UserEntity extends PersonEntity {

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isAdmin;

}

