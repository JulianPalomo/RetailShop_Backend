package com.finalProyect.retailShop_Backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data  // Genera automáticamente getters, setters, equals, hashcode y toString
@NoArgsConstructor  // Genera un constructor sin argumentos
@AllArgsConstructor  // Genera un constructor con todos los argumentos
@Builder  // Facilita la creación de objetos con el patrón builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
}


