package com.finalProyect.retailShop_Backend.entities.persons;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Table(name = "persona")
@SuperBuilder
public abstract class PersonEntity {

    // Constructor sin argumentos necesario para Lombok
    public PersonEntity() {
    }

    // Constructor adicional si necesitas inicializar con SuperBuilder
    public PersonEntity(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

}

