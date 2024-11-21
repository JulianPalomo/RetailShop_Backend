package com.finalProyect.retailShop_Backend.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;


@Entity
@Table(name = "sale_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private SaleEntity sale;  // Carrito al que pertenece el producto

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;  // Producto que se agrega al carrito

    @Column(nullable = false)
    private int quantity;  // Cantidad del producto en el carrito

    @Column(nullable = false)
    private BigDecimal unitPrice;  // Precio unitario del producto en el carrito

    @Column(nullable = false)
    private BigDecimal subTotal;  // Subtotal (unitPrice * quantity)
}