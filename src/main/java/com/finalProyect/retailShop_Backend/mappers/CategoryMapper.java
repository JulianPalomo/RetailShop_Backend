package com.finalProyect.retailShop_Backend.mappers;

import com.finalProyect.retailShop_Backend.DTO.CategoryDto;
import com.finalProyect.retailShop_Backend.entities.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto toDto(CategoryEntity category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    public CategoryEntity toEntity(CategoryDto dto) {
        CategoryEntity category = new CategoryEntity();
        category.setId(dto.getId());
        category.setName(dto.getName());
        return category;
    }
}