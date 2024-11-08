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

    /* TODO no anda !!!!!!!!!!!!!
    @Query("SELECT new com.tu.paquete.dto.ProductDto(" +
            "p.id, " +
            "p.name, " +
            "p.price, " +
            "p.isActive, " +
            "c.name, " +
            "s.quantity, " +
            "b.name) " +
            "FROM ProductEntity p " +
            "LEFT JOIN p.stock s " +  // Usando la relación OneToOne correctamente
            "LEFT JOIN p.brand b " +  // Usando la relación ManyToOne correctamente
            "LEFT JOIN p.category c " +  // Usando la relación ManyToOne correctamente
            "WHERE (:id IS NULL OR p.id = :id) " +
            "AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:category IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :category, '%'))) " +
            "AND (:brandName IS NULL OR LOWER(b.name) LIKE LOWER(CONCAT('%', :brandName, '%'))) ")
    List<ProductDto> getAllProducts(@Param("id") Long id,
                                    @Param("name") String name,
                                    @Param("category") String category,
                                    @Param("brandName") String brandName);
*/
}
