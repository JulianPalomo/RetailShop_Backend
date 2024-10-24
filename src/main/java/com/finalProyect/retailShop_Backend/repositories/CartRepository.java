package com.finalProyect.retailShop_Backend.repositories;

import com.finalProyect.retailShop_Backend.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
}
