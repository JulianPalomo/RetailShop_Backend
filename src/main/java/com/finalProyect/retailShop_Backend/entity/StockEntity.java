package com.finalProyect.retailShop_Backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "stock")
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)

    private List<ProductEntity> producto;

}
