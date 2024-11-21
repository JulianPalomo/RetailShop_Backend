package com.finalProyect.retailShop_Backend.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
/*
    // Relación bidireccional: un Category tiene muchos Products
    @OneToMany(mappedBy = "category")  // "category" es el nombre del atributo en ProductEntity
    @JsonIgnore
    private List<ProductEntity> products; //esta parte es opc pero nos facilita filtrados por categoria (ver si al ser filtrados compuesto sirve igual)
*/
    public CategoryEntity(String name) {
        this.name = name;
    }

}