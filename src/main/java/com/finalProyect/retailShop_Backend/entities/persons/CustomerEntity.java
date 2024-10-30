package com.finalProyect.retailShop_Backend.entities.persons;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data  // Genera automáticamente getters, setters, equals, hashcode y toString
@NoArgsConstructor  // Genera un constructor sin argumentos
@AllArgsConstructor  // Genera un constructor con todos los argumentos
@Builder  // Facilita la creación de objetos con el patrón builder
public class CustomerEntity extends PersonEntity{

    @Column(nullable = true)
    private String address;

    @Column(nullable = false)
    private int idNumber;

}
