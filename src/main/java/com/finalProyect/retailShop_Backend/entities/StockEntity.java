package com.finalProyect.retailShop_Backend.entities;

import com.finalProyect.retailShop_Backend.entities.products.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock")
@Data  // Genera automáticamente getters, setters, equals, hashcode y toString
@NoArgsConstructor  // Genera un constructor sin argumentos
@AllArgsConstructor  // Genera un constructor con todos los argumentos
@Builder
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int stock;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;
}
