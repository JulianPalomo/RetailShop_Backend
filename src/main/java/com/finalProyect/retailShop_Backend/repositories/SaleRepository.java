package com.finalProyect.retailShop_Backend.repositories;

import com.finalProyect.retailShop_Backend.entities.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<SaleEntity, Long> {
}
