package com.finalProyect.retailShop_Backend.controllers;

import com.finalProyect.retailShop_Backend.DTO.ProductDto;
import com.finalProyect.retailShop_Backend.exceptions.ProductNotFoundException;
import com.finalProyect.retailShop_Backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;


import java.util.List;


@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200") // Asegúrate de que la URL del frontend sea correcta

public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @Operation(summary = "Obtiene una lista de productos con filtros opcionales",
            description = "Devuelve una lista de productos. Puedes filtrar por id, nombre o categoría.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta: los datos proporcionados son inválidos"),
            @ApiResponse(responseCode = "404", description = "No se encontraron productos que coincidan con los filtros"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ProductDto>> getAllProducts(
            @RequestParam(required = false) Long sku,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String category) {

        List<ProductDto> products = productService.getAllProducts(sku, description, category);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un producto por su ID",
            description = "Devuelve un producto específico según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta: el ID proporcionado no es válido"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado para el ID proporcionado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) throws ProductNotFoundException {
        ProductDto product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo producto",
            description = "Permite crear un nuevo producto en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta: los datos proporcionados son inválidos o incompletos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        System.out.println(productDto); // Revisa si el objeto llega con valores válidos

        ProductDto createdProduct = productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un producto existente",
            description = "Permite actualizar los detalles de un producto utilizando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta: los datos proporcionados son inválidos"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado con el ID proporcionado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) throws ProductNotFoundException {
        ProductDto updatedProduct = productService.updateProduct(id, productDto);
        if (updatedProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un producto por su ID",
            description = "Permite eliminar un producto del sistema utilizando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado para el ID proporcionado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        boolean isDeleted = productService.deleteProduct(id);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }

}