package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.DTO.CategoryDto;
import com.finalProyect.retailShop_Backend.entities.CategoryEntity;
import com.finalProyect.retailShop_Backend.mappers.CategoryMapper;
import com.finalProyect.retailShop_Backend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper; // Mapper para convertir entre entidad y DTO

    // Método para obtener todas las categorías
    public List<CategoryDto> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();

        return categories.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    // Método para obtener una categoría por ID
    public CategoryDto getCategoryById(Long id) {
        CategoryEntity category =  categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found")); // TODO: agregar excepcion personalizada

        return categoryMapper.toDto(category);
    }

    // Método para crear una nueva categoría
    public CategoryDto createCategory(CategoryEntity category) {
        CategoryEntity savedCategory = categoryRepository.save(category);

        return categoryMapper.toDto(savedCategory);
    }

    // Método para actualizar una categoría
    public CategoryDto updateCategory(Long id, CategoryEntity categoryDetails) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(categoryDetails.getName());

        CategoryEntity updatedCategory = categoryRepository.save(category);

        return categoryMapper.toDto(updatedCategory);
    }

    // Método para eliminar una categoría
    public void deleteCategory(Long id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }

    //fixme: se puece agregar un metodo para retornar los prodcutos de cierta categoria
}