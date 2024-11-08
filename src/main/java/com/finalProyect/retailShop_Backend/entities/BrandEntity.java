package com.finalProyect.retailShop_Backend.entities;

import com.finalProyect.retailShop_Backend.entities.products.ProductEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "brands")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // Relación bidireccional: una marca tiene muchos productos
    @OneToMany(mappedBy = "brand")  // "brand" es el nombre del atributo en ProductEntity
    private List<ProductEntity> products; //opcional, facilita consultas por marca

}
