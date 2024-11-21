package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.entity.ProductEntity;
import com.finalProyect.retailShop_Backend.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

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

            if (updatedProduct.getDescription() != null) {
                product.setDescription(updatedProduct.getDescription());
            }
            // Actualizar el stock si existe
            if (product.getStock() != null && updatedProduct.getStock() != null) {
                product.getStock().setStock(updatedProduct.getStock().getStock());
            }

            return productRepository.save(product);
        } else {
            return null; // o puede ser una excepcion aca
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

