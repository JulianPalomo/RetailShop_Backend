package com.finalProyect.retailShop_Backend.repositories;

import com.finalProyect.retailShop_Backend.DTO.ProductWithDetailsDTO;
import com.finalProyect.retailShop_Backend.entities.products.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductWithDetailsDTO, Long> {

    //Query personalizada para filtrar por cualquiera de los parametros, si se pasan nulls no los tiene en cuenta
    @Query("SELECT new com.tu.paquete.dto.ProductWithDetailsDTO(" +
            "p.id, " +
            "p.name, " +
            "p.price, " +
            "p.isActive, " +
            "p.category.name, " +
            "s.quantity, " +
            "b.name) " +
            "FROM ProductEntity p " +
            "LEFT JOIN StockEntity s ON p.id = s.product.id " +
            "LEFT JOIN ProductXBrandEntity pxbe ON p.id = pxbe.product.id " +
            "LEFT JOIN BrandEntity b ON pxbe.brand.id = b.id " +
            "WHERE (:id IS NULL OR p.id = :id) " +
            "AND (:name IS NULL OR p.name LIKE %:name%) " +
            "AND (:category IS NULL OR p.category.name LIKE %:category%) " +
            "AND (:brandName IS NULL OR b.name LIKE %:brandName%)")
    List<ProductWithDetailsDTO> filterProductsWithParams(Long id, String name, String category, String brandName);

    @Query("SELECT new com.tu.paquete.dto.ProductWithDetailsDTO(" +
            "p.id, " +
            "p.name, " +
            "p.price, " +
            "p.isActive, " +
            "p.category.name, " +        // Asegúrate de tener un campo 'name' en CategoryEntity
            "s.quantity, " +             // Esto es de la tabla StockEntity
            "b.name) " +                 // Nombre de la marca desde la relación ProductXBrandEntity
            "FROM ProductEntity p " +
            "LEFT JOIN StockEntity s ON p.id = s.product.id " +
            "LEFT JOIN ProductXBrandEntity pxbe ON p.id = pxbe.product.id " +
            "LEFT JOIN BrandEntity b ON pxbe.brand.id = b.id " +
            "WHERE (:id IS NULL OR p.id = :id) " +
            "AND (:name IS NULL OR p.name LIKE %:name%) " +
            "AND (:category IS NULL OR p.category.name LIKE %:category%) " +
            "AND (:brandName IS NULL OR b.name LIKE %:brandName%)")
    List<ProductWithDetailsDTO> getAllProducts ();
}
