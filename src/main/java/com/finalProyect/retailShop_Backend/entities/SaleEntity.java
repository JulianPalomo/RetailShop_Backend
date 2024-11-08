package com.finalProyect.retailShop_Backend.entities;


import com.finalProyect.retailShop_Backend.entities.persons.UserEntity;
import com.finalProyect.retailShop_Backend.entities.products.CartProductEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ventas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ID de la venta, generado automáticamente

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;  // Usuario que realiza la venta

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "venta_id")
    private List<CartProductEntity> products;  // Productos incluidos en la venta

    @Column(nullable = false)
    private BigDecimal total;  // Total de la venta

    @Column(nullable = false)
    private LocalDate date;  // Fecha de la venta

    @Column(nullable = false)
    private String paymentMethod;  // Método de pago de la venta
}
