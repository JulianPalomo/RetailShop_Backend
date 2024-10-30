package com.finalProyect.retailShop_Backend.entities.products;

import com.finalProyect.retailShop_Backend.entities.CartEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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
        @JoinColumn(name = "cart_id", nullable = false)
        private CartEntity cart;

        @ManyToOne
        @JoinColumn(name = "purchased_product_id", nullable = false)
        private PurchasedProductEntity purchasedProduct;
}