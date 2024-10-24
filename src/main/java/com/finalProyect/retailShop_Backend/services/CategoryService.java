package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.entity.CategoryEntity;
import com.finalProyect.retailShop_Backend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Obtener todas las categorías
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Obtener una categoría por ID
    public Optional<CategoryEntity> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    // Crear una nueva categoría
    public CategoryEntity createCategory(CategoryEntity category) {
        return categoryRepository.save(category);
    }

    // Actualizar una categoría existente
    public CategoryEntity updateCategory(Long id, CategoryEntity updatedCategory) {
        Optional<CategoryEntity> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            CategoryEntity category = categoryOptional.get();
            category.setName(updatedCategory.getName());
            return categoryRepository.save(category);
        } else {
            return null; // O lanzar una excepción personalizada
        }
    }

    // Eliminar una categoría por ID
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
