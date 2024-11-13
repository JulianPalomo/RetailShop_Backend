package com.finalProyect.retailShop_Backend.repositories;

import com.finalProyect.retailShop_Backend.entities.CartEntity;
import com.finalProyect.retailShop_Backend.entities.SaleEntity;
import com.finalProyect.retailShop_Backend.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaleRepository extends JpaRepository<SaleEntity, Long> {
}