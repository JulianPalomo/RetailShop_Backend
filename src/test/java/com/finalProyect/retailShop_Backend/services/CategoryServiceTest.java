package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.DTO.CategoryDto;
import com.finalProyect.retailShop_Backend.entities.CategoryEntity;
import com.finalProyect.retailShop_Backend.mappers.CategoryMapper;
import com.finalProyect.retailShop_Backend.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @InjectMocks
    private CategoryService service;

    @Mock
    private CategoryRepository repository;

    @Mock
    private CategoryMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializar mocks
    }

    @Test
    void test_get_by_id_success(){
        // Arrange
        Long id = 1L;
        String name = "Lucas";
        CategoryEntity entity = new CategoryEntity();
        entity.setId(id);
        entity.setName(name);
        CategoryDto dto = new CategoryDto(id,name);
        when(mapper.toDto(entity)).thenReturn(dto);
        // Configurar mocks
        when(repository.findById(id)).thenReturn(Optional.of(entity)); // Mock del repositorio
        when(mapper.toDto(entity)).thenReturn(dto);

        // Act
        CategoryDto result = service.getCategoryById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(name, result.getName());

        // Verificar interacciones
        verify(repository).findById(id);
        verify(mapper).toDto(entity);
    }

    @Test
    void test_get_by_id_not_found() {
        // Arrange
        Long id = 2L;

        // Configurar mocks
        when(repository.findById(id)).thenReturn(Optional.empty()); // Simular que no se encuentra el ID

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.getCategoryById(id));
        assertEquals("Category not found", exception.getMessage());

        // Verificar interacciones
        verify(repository).findById(id);
        verifyNoInteractions(mapper);
    }

    // ****************************************

    @Test
    void test_createCategory_existingCategory() {
        // Arrange
        String categoryName = "Electronics";
        Long categoryId = 1L;

        CategoryEntity existingEntity = new CategoryEntity();
        existingEntity.setId(categoryId);
        existingEntity.setName(categoryName);

        CategoryDto categoryDto = new CategoryDto(null, categoryName);
        CategoryDto existingCategoryDto = new CategoryDto(categoryId, categoryName);

        // Configurar mocks
        when(repository.findByName(categoryName)).thenReturn(Optional.of(existingEntity));
        when(mapper.toDto(existingEntity)).thenReturn(existingCategoryDto);

        // Act
        CategoryDto result = service.createCategory(categoryDto);

        // Assert
        assertNotNull(result);
        assertEquals(categoryId, result.getId());
        assertEquals(categoryName, result.getName());

        // Verificar interacciones
        verify(repository).findByName(categoryName);
        verify(mapper).toDto(existingEntity);
        verifyNoMoreInteractions(repository);

        // Cambiar la verificación para evitar problemas
        verify(mapper, never()).toEntity(any());
    }

    @Test
    void test_createCategory_newCategory() {
        // Arrange
        String categoryName = "Furniture";
        Long categoryId = 2L;

        CategoryDto categoryDto = new CategoryDto(null, categoryName);
        CategoryEntity newEntity = new CategoryEntity();
        newEntity.setName(categoryName);

        CategoryEntity savedEntity = new CategoryEntity();
        savedEntity.setId(categoryId);
        savedEntity.setName(categoryName);

        CategoryDto savedCategoryDto = new CategoryDto(categoryId, categoryName);

        // Configurar mocks
        when(repository.findByName(categoryName)).thenReturn(Optional.empty());
        when(mapper.toEntity(categoryDto)).thenReturn(newEntity);
        when(repository.save(newEntity)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(savedCategoryDto);

        // Act
        CategoryDto result = service.createCategory(categoryDto);

        // Assert
        assertNotNull(result);
        assertEquals(categoryId, result.getId());
        assertEquals(categoryName, result.getName());

        // Verificar interacciones
        verify(repository).findByName(categoryName);
        verify(mapper).toEntity(categoryDto);
        verify(repository).save(newEntity);
        verify(mapper).toDto(savedEntity);
    }

}