package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.DTO.ProductDto;
import com.finalProyect.retailShop_Backend.Specification.ProductSpecification;
import com.finalProyect.retailShop_Backend.entities.CategoryEntity;
import com.finalProyect.retailShop_Backend.entities.ProductEntity;
import com.finalProyect.retailShop_Backend.exceptions.ProductNotFoundException;
import com.finalProyect.retailShop_Backend.mappers.ProductMapper;
import com.finalProyect.retailShop_Backend.repositories.CategoryRepository;
import com.finalProyect.retailShop_Backend.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<ProductDto> getAllProducts(String sku, String name, String category, Pageable pageable) {
        Specification<ProductEntity> spec = Specification.where(ProductSpecification.isActiveTrue())
                .and(ProductSpecification.skuLike(sku))
                .and(ProductSpecification.nameLike(name))
                .and(ProductSpecification.categoryLike(category));

        Page<ProductEntity> productPage = productRepository.findAll(spec, pageable);

        return productPage.map(productMapper::toDto);
    }


    public ProductDto createProduct(ProductDto productDto) {
        CategoryEntity category = categoryRepository.findByName(productDto.getCategory().getName())
                .orElseGet(() -> categoryRepository.save(new CategoryEntity(productDto.getCategory().getName())));

        ProductEntity product = new ProductEntity();
        product.setName(productDto.getDescription());
        product.setPrice(productDto.getUnitPrice());
        product.setCategory(category);
        product.setStock(productDto.getStock());
        product.setSku(productDto.getSku());

        product = productRepository.save(product);

        return productMapper.toDto(product);
    }


    public ProductDto updateProduct(Long id, ProductDto productDto) throws ProductNotFoundException {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con el ID: " + id));

        CategoryEntity category = categoryRepository.findByName(productDto.getCategory().getName())
                .orElseGet(() -> categoryRepository.save(new CategoryEntity(productDto.getCategory().getName())));

        product.setName(productDto.getDescription());
        product.setSku(productDto.getSku());
        product.setPrice(productDto.getUnitPrice());
        product.setCategory(category);
        product.setStock(productDto.getStock());
        product.setMinimumStock(productDto.getMinimumStock());

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

    public boolean existsBySku(String sku) {
        return productRepository.existsBySku(sku);
    }
}