package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.DTO.ProductDto;
import com.finalProyect.retailShop_Backend.entities.CategoryEntity;
import com.finalProyect.retailShop_Backend.entities.products.ProductEntity;
import com.finalProyect.retailShop_Backend.exceptions.ProductNotFoundException;
import com.finalProyect.retailShop_Backend.mappers.ProductMapper;
import com.finalProyect.retailShop_Backend.repositories.CategoryRepository;
import com.finalProyect.retailShop_Backend.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    public ProductService() {
    }

    public List<ProductDto> getAllProducts(Long id, String name, String category) {
        List<ProductEntity> productEntities = productRepository.findAll();

        return productEntities.stream()
                .filter(ProductEntity::getIsActive) // Filtra solo productos activos
                .filter(product -> (id == null || product.getId().equals(id)))
                .filter(product -> (name == null || product.getName().toLowerCase().contains(name.toLowerCase())))
                .filter(product -> (category == null || product.getCategory().getName().toLowerCase().contains(category.toLowerCase())))
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto createProduct(ProductDto productDto) {
        CategoryEntity category = categoryRepository.findByName(productDto.getCategory().getName())
                .orElseGet(() -> categoryRepository.save(new CategoryEntity(productDto.getCategory().getName())));

        ProductEntity product = new ProductEntity();
        product.setName(productDto.getDescription());
        product.setPrice(productDto.getUnitPrice());
        product.setCategory(category);
        product.setStock(productDto.getStock());

        product = productRepository.save(product);

        return productMapper.toDto(product);
    }


    public ProductDto updateProduct(Long id, ProductDto productDto) throws ProductNotFoundException {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con el ID: " + id));

        CategoryEntity category = categoryRepository.findByName(productDto.getCategory().getName())
                .orElseGet(() -> categoryRepository.save(new CategoryEntity(productDto.getCategory().getName())));

        product.setName(productDto.getDescription());
        product.setPrice(productDto.getUnitPrice());
        product.setCategory(category);
        product.setStock(productDto.getStock());

        ProductEntity updatedProduct = productRepository.save(product);

        return productMapper.toDto(updatedProduct);
    }


    public boolean deleteProduct(Long id) throws ProductNotFoundException {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con el ID: " + id));

        product.setIsActive(false);
        productRepository.save(product);
        return true;
    }


    public ProductDto getProductById(Long id) throws ProductNotFoundException {
        ProductEntity product = productRepository.findById(id)
                .filter(ProductEntity::getIsActive)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con el ID: " + id));

        ProductDto productDto = productMapper.toDto(product);
        productDto.setCategory(product.getCategory());
        productDto.setStock(product.getStock());

        return productDto;
    }
}