package com.finalProyect.retailShop_Backend.entities;

import com.finalProyect.retailShop_Backend.entities.products.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long quantity;

    @OneToOne(mappedBy = "stock")
    private ProductEntity product;  // Relación inversa con Producto

    @Column(nullable = false)
    private int MinimunStock;
}
