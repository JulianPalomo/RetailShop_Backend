package com.finalProyect.retailShop_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchasedProductXCartEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "order_id", nullable = false)
        private CartEntity order;

        @ManyToOne
        @JoinColumn(name = "product_id", nullable = false)
        private PurchasedProductEntity purchasedProduct;

        @Column(nullable = false)
        private BigDecimal unitPrice;

        @Column(nullable = false)
        private int quantity;

        @Column(nullable = false)
        private BigDecimal subtotal;  // Subtotal por producto
}