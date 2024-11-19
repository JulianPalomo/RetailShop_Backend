package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.DTO.ProductDto;
import com.finalProyect.retailShop_Backend.entities.BrandEntity;
import com.finalProyect.retailShop_Backend.entities.CategoryEntity;
import com.finalProyect.retailShop_Backend.entities.StockEntity;
import com.finalProyect.retailShop_Backend.entities.products.ProductEntity;
import com.finalProyect.retailShop_Backend.exceptions.ProductNotFoundException;
import com.finalProyect.retailShop_Backend.mappers.ProductMapper;
import com.finalProyect.retailShop_Backend.repositories.BrandRepository;
import com.finalProyect.retailShop_Backend.repositories.CategoryRepository;
import com.finalProyect.retailShop_Backend.repositories.ProductRepository;
import com.finalProyect.retailShop_Backend.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper; //no se si es totalmente necesario ya que la query devuelve la estructura correcta

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /* TODO: este no anda por la query del repo
    //Metodo que obtiene los productos con los filtros opcionales
    public List<ProductDto> getAllProducts(Long id, String name, String category, String brandName) {
        // Se pasa al repositorio los parámetros de filtrado
        return productRepository.getAllProducts(id, name, category, brandName);
    }
*/
    public List<ProductDto> getAllProducts(Long id, String name, String category, String brandName) {
        // Obtiene todos los productos sin filtros
        List<ProductEntity> productEntities = productRepository.findAll();  // Método findAll() o puedes ajustar según sea necesario

        // Filtra los productos en memoria si hay parámetros
        return productEntities.stream()
                .filter(product -> (id == null || product.getId().equals(id)))
                .filter(product -> (name == null || product.getName().toLowerCase().contains(name.toLowerCase())))
                .filter(product -> (category == null || product.getCategory().getName().toLowerCase().contains(category.toLowerCase())))
                .filter(product -> (brandName == null || product.getBrand().getName().toLowerCase().contains(brandName.toLowerCase())))
                .map(productMapper::toDto)  // Mapea los productos a DTO
                .collect(Collectors.toList());
    }
    public ProductDto createProduct(ProductDto productDto) {
        // Verificar o crear la categoría si no existe
        CategoryEntity category = categoryRepository.findByName(productDto.getCategoryName())
                .orElseGet(() -> categoryRepository.save(new CategoryEntity(productDto.getCategoryName())));

        // Verificar o crear la marca si no existe
        BrandEntity brand = brandRepository.findByName(productDto.getBrandName())
                .orElseGet(() -> brandRepository.save(new BrandEntity(productDto.getBrandName())));

        StockEntity stock = new StockEntity();
        stock.setQuantity(productDto.getStockQuantity());
        stock.setMinimunStock(productDto.getMinimunStock());
        stock = stockRepository.save(stock);

        ProductEntity product = new ProductEntity();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);
        product.setBrand(brand);
        product.setStock(stock);
        product.setIsActive(productDto.isIsActive());
        product.setSku(productDto.getSku());


        product = productRepository.save(product);

        return productMapper.toDto(product);
    }

    public ProductDto updateProduct(Long id, ProductDto productDto){
        // Buscar el producto en la base de datos
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Buscar o crear la categoría
        CategoryEntity category = categoryRepository.findByName(productDto.getCategoryName())
                .orElseGet(() -> categoryRepository.save(new CategoryEntity(productDto.getCategoryName())));

        // Buscar o crear la marca
        BrandEntity brand = brandRepository.findByName(productDto.getBrandName())
                .orElseGet(() -> brandRepository.save(new BrandEntity(productDto.getBrandName())));

        // Actualizar el producto con los valores del DTO
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);
        product.setBrand(brand);
        product.setIsActive(productDto.isIsActive());
        product.getStock().setQuantity(productDto.getStockQuantity());
        product.getStock().setMinimunStock(productDto.getMinimunStock());  // Actualizar el mínimo stock


        ProductEntity updatedProduct = productRepository.save(product);

        // Mapear la entidad actualizada a DTO y devolverla
        return productMapper.toDto(updatedProduct);
    } //TODO agregar excepcion

    // Método para eliminar un producto
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }


    public ProductDto getProductById(Long id) {
        ProductEntity product = null;
        try {
            product = productRepository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        } catch (ProductNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Mapear a DTO con detalles adicionales fixme: aca seria mejor hacer la consulta por el repositorio (mas eficiente)
        ProductDto productDto = productMapper.toDto(product);
        productDto.setCategoryName(product.getCategory().getName());
        productDto.setStockQuantity(product.getStock().getQuantity());
        productDto.setBrandName(product.getBrand().getName());
        productDto.setMinimunStock(product.getStock().getMinimunStock());
        productDto.setIsActive(product.isIsActive());
        return productDto;
    }

}

