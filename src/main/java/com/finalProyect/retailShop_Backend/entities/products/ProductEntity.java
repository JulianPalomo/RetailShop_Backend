package com.finalProyect.retailShop_Backend.entities.products;

import com.finalProyect.retailShop_Backend.entities.BrandEntity;
import com.finalProyect.retailShop_Backend.entities.CategoryEntity;
import com.finalProyect.retailShop_Backend.entities.StockEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // El ID es generado automáticamente

    @Column(nullable = false, unique = true)
    private String name;  // Nombre del producto

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private BigDecimal price;  // Precio del producto

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;  // Relación con la tabla de categorías

    @OneToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private StockEntity stock;  // Relación con la tabla de stock

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private BrandEntity brand;  // Relación con la tabla de marcas

    @Column(nullable = false)
    private boolean IsActive;  // Indica si el producto está activo o no

}