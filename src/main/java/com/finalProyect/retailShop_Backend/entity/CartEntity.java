package com.finalProyect.retailShop_Backend.entity;
import com.finalProyect.retailShop_Backend.enums.CartStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "purchases")
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
    private UserEntity user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchasedProductXCartEntity> purchasedProducts;  // Relación con PurchasedProductXCartEntity

    @Column(nullable = false)
    private BigDecimal total;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String paymentMethod;

    @Column(nullable = false)
    private String customerDNI;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CartStatus cartStatus = CartStatus.PENDING;
}
