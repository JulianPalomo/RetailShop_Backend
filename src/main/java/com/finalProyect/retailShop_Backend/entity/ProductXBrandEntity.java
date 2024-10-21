package com.finalProyect.retailShop_Backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_brand")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductXBrandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private BrandEntity brand;
}
