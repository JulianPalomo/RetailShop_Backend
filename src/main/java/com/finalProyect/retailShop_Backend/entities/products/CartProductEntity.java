package com.finalProyect.retailShop_Backend.entities.products;

import com.finalProyect.retailShop_Backend.entities.CartEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;


@Entity
@Table(name = "cart_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private CartEntity cart;  // Carrito al que pertenece el producto

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;  // Producto que se agrega al carrito

    @Column(nullable = false)
    private Long quantity;  // Cantidad del producto en el carrito

    @Column(nullable = false)
    private BigDecimal unitPrice;  // Precio unitario del producto en el carrito

    @Column(nullable = false)
    private BigDecimal subTotal;  // Subtotal (unitPrice * quantity)


}
