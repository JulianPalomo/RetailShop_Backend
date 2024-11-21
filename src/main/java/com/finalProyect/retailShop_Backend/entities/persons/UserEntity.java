package com.finalProyect.retailShop_Backend.entities.persons;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "users")
@Data  // Genera autoincrement getters, setters, equals, hashcode y toString
@NoArgsConstructor  // Genera un constructor sin argumentos
@AllArgsConstructor  // Genera un constructor con todos los argumentos
@SuperBuilder  // Facilita la creación de objetos con el patrón builder
public class UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String password;

    @Column(unique = true,nullable = false)
    private String dni;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean isAdmin;

    @Column(nullable = false)
    private boolean isActive = true;

}