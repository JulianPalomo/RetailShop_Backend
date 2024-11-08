package com.finalProyect.retailShop_Backend.entities;
import com.finalProyect.retailShop_Backend.entities.persons.UserEntity;
import com.finalProyect.retailShop_Backend.entities.products.CartProductEntity;
import com.finalProyect.retailShop_Backend.enums.CartStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;  // Vendedor que maneja el carrito

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartProductEntity> cartProducts;  // Relación con los productos en el carrito

    @Column(nullable = false)
    private BigDecimal total;  // Total del carrito

    @Column(nullable = false)
    private LocalDate date;  // Fecha en la que se genera el carrito

    @Column(nullable = false)
    private String paymentMethod;  // Método de pago


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CartStatus status = CartStatus.PENDING;  // Estado del carrito
}