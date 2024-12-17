package com.finalProyect.retailShop_Backend.Specification;

import com.finalProyect.retailShop_Backend.entities.ProductEntity;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<ProductEntity> isActiveTrue() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isActive"), true); // Filtra solo los productos activos
    }

    public static Specification<ProductEntity> skuLike(String sku) {
        return (root, query, criteriaBuilder) ->
                sku == null ? criteriaBuilder.conjunction() : criteriaBuilder.like(criteriaBuilder.lower(root.get("sku")), "%" + sku.toLowerCase() + "%");
    }

    public static Specification<ProductEntity> nameLike(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? criteriaBuilder.conjunction() : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<ProductEntity> categoryLike(String category) {
        return (root, query, criteriaBuilder) ->
                category == null ? criteriaBuilder.conjunction() : criteriaBuilder.like(criteriaBuilder.lower(root.get("category").get("name")), "%" + category.toLowerCase() + "%");
    }
}
