package com.finalProyect.retailShop_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "purchased_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchasedProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "purchasedProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchasedProductXCartEntity> orders;  // Relación con ProductXOrderEntity

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)  // Referencia a la categoría
    private CategoryEntity category;  // Cada producto tiene una única categoría


    public BigDecimal calculateSubtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

}
