package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.entities.products.ProductEntity;
import com.finalProyect.retailShop_Backend.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<ProductEntity> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public ProductEntity createProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    public ProductEntity updateProduct(Long id, ProductEntity updatedProduct) {
        Optional<ProductEntity> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            ProductEntity product = productOptional.get();
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setCategory(updatedProduct.getCategory());
            product.setDescription(updatedProduct.getDescription());
            return productRepository.save(product);
        } else {
            return null; // O lanzar una excepción personalizada
        }
    }


    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

