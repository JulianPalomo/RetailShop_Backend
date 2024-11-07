package com.finalProyect.retailShop_Backend.entities.products;

import com.finalProyect.retailShop_Backend.entities.CategoryEntity;
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
@Inheritance(strategy = InheritanceType.JOINED)
public class ProductEntity {

    // Constructor adicional si necesitas inicializar con SuperBuilder
    public ProductEntity(Long id, String name, BigDecimal price, boolean isActive,CategoryEntity category ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this. isActive = isActive;
        this.category = category;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column()
    private String description;

    @Column(nullable = false)
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

}