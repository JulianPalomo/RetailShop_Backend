package com.finalProyect.retailShop_Backend.entities;
import com.finalProyect.retailShop_Backend.entities.persons.CustomerEntity;
import com.finalProyect.retailShop_Backend.entities.persons.UserEntity;
import com.finalProyect.retailShop_Backend.entities.products.PurchasedProductXCartEntity;
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

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)  // Nullable, ya que no siempre estará asociado
    private CustomerEntity customer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CartStatus cartStatus = CartStatus.PENDING;
}
