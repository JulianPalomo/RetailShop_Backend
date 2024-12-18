package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.DTO.CategoryDto;
import com.finalProyect.retailShop_Backend.entities.CategoryEntity;
import com.finalProyect.retailShop_Backend.mappers.CategoryMapper;
import com.finalProyect.retailShop_Backend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public CategoryDto createCategory(CategoryDto categoryDto) {
        // Verificar si la categoría ya existe
        Optional<CategoryEntity> existingCategory = categoryRepository.findByName(categoryDto.getName());
        if (existingCategory.isPresent()) {
            // Si ya existe, devolver el DTO de la categoría existente
            return categoryMapper.toDto(existingCategory.get());
        }

        // Crear una nueva categoría a partir del DTO
        CategoryEntity categoryEntity = categoryMapper.toEntity(categoryDto);

        // Guardar la nueva categoría en la base de datos
        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);

        // Convertir la categoría guardada a DTO y devolverla
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