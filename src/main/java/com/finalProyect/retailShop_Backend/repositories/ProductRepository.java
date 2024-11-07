package com.finalProyect.retailShop_Backend.repositories;

import com.finalProyect.retailShop_Backend.entities.products.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    //Query personalizada para filtrar por cualquiera de los parametros, si se pasan nulls no los tiene en cuenta
    @Query("SELECT p FROM ProductEntity p WHERE (:id IS NULL OR p.id = :id) " +
            "AND (:name IS NULL OR p.name LIKE %:name%) " +
            "AND (:category IS NULL OR p.category = :category)")
    List<ProductEntity> filterProducts(Long id, String name, String category);

}
