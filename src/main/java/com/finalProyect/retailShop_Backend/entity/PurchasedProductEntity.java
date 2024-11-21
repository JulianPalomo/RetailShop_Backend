package com.finalProyect.retailShop_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "purchased_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PurchasedProductEntity extends ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private BigDecimal subTotal;
}