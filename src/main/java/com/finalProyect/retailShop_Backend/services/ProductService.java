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
    private ProductMapper productMapper; //no se si es totalmente necesario ya que la query devuelve la estructura correcta

    public ProductService() {
    }

    // Obtener todos los productos con filtros
    public List<ProductDto> getAllProducts(Long id, String name, String category) {
        List<ProductEntity> productEntities = productRepository.findAll();

        return productEntities.stream()
                .filter(product -> (id == null || product.getId().equals(id)))
                .filter(product -> (name == null || product.getName().toLowerCase().contains(name.toLowerCase())))
                .filter(product -> (category == null || product.getCategory().getName().toLowerCase().contains(category.toLowerCase())))
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    // Crear un nuevo producto
    public ProductDto createProduct(ProductDto productDto) {
        CategoryEntity category = categoryRepository.findByName(productDto.getCategoryName())
                .orElseGet(() -> categoryRepository.save(new CategoryEntity(productDto.getCategoryName())));

        ProductEntity product = new ProductEntity();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);
        product.setStock(product.getStock());

        product = productRepository.save(product);

        return productMapper.toDto(product);
    }

    // Actualizar un producto
    public ProductDto updateProduct(Long id, ProductDto productDto) throws ProductNotFoundException {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con el ID: " + id));

        CategoryEntity category = categoryRepository.findByName(productDto.getCategoryName())
                .orElseGet(() -> categoryRepository.save(new CategoryEntity(productDto.getCategoryName())));

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);
        product.setStock(productDto.getStockQuantity());

        ProductEntity updatedProduct = productRepository.save(product);

        return productMapper.toDto(updatedProduct);
    }

    // Eliminar un producto
    public boolean deleteProduct(Long id) throws ProductNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Producto no encontrado con el ID: " + id);
        }
        productRepository.deleteById(id);
        return true;
    }

    // Obtener un producto por ID
    public ProductDto getProductById(Long id) throws ProductNotFoundException {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con el ID: " + id));

        ProductDto productDto = productMapper.toDto(product);
        productDto.setCategoryName(product.getCategory().getName());
        productDto.setStockQuantity(product.getStock());

        return productDto;
    }
}