package com.finalProyect.retailShop_Backend.repositories;

import com.finalProyect.retailShop_Backend.DTO.ProductDto;
import com.finalProyect.retailShop_Backend.entities.products.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findAll();  // Método para obtener todos los productos

}
