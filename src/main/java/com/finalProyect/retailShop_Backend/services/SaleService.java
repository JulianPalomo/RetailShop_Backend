package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.DTO.SaleDto;
import com.finalProyect.retailShop_Backend.entities.ProductEntity;
import com.finalProyect.retailShop_Backend.entities.SaleEntity;
import com.finalProyect.retailShop_Backend.entities.persons.UserEntity;
import com.finalProyect.retailShop_Backend.entities.CartProductEntity;
import com.finalProyect.retailShop_Backend.mappers.SaleMapper;
import com.finalProyect.retailShop_Backend.repositories.CartProductRepository;
import com.finalProyect.retailShop_Backend.repositories.ProductRepository;
import com.finalProyect.retailShop_Backend.repositories.SaleRepository;
import com.finalProyect.retailShop_Backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private SaleMapper saleMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartProductRepository cartProductRepository; // Inyectamos CartProductRepository
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public SaleDto confirmarVenta(SaleDto saleDto) {
        // 1. Obtener el usuario basado en saleDto.getEmployeedId()
        UserEntity user = userRepository.findById(saleDto.getEmployeedId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        // 2. Obtener y validar cada producto del DTO
        List<CartProductEntity> cartProductEntities = saleDto.getProducts().stream()
                .map(productDto -> {
                    // Buscar el ProductEntity asociado al SKU
                    ProductEntity productEntity = productRepository.findById(productDto.getId())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productDto.getSku()));

                    // Crear una nueva instancia de CartProductEntity
                    CartProductEntity cartProductEntity = new CartProductEntity();
                    cartProductEntity.setProduct(productEntity);
                    cartProductEntity.setQuantity(productDto.getQuantity());
                    cartProductEntity.setUnitPrice(productDto.getUnitPrice());
                    cartProductEntity.setSubTotal(productDto.getSubTotal());
                    return cartProductEntity;
                })
                .collect(Collectors.toList());

        // 3. Crear y persistir la venta
        SaleEntity saleEntity = new SaleEntity();
        saleEntity.setUser(user);
        saleEntity.setProducts(cartProductEntities); // Relación con los productos
        saleEntity.setTotal(saleDto.getTotal());
        saleEntity.setDate(LocalDate.now()); // Fecha actual
        saleEntity.setPaymentMethod(saleDto.getPaymentMethod());

        // Guardar la venta en la base de datos
        SaleEntity savedSale = saleRepository.save(saleEntity);

        // 4. Asociar la venta a cada producto y guardar los productos
        cartProductEntities.forEach(cartProductEntity -> cartProductEntity.setSale(savedSale));
        cartProductRepository.saveAll(cartProductEntities);

        // 5. Retornar la venta guardada como DTO
        return saleMapper.toDto(savedSale);
    }

    public List<SaleDto> obtenerTodasLasVentas() {
        return saleRepository.findAll().stream()
                .map(saleMapper::toDto)
                .collect(Collectors.toList());
    }

    public SaleDto obtenerVentaPorId(Long id) {
        Optional<SaleEntity> venta = saleRepository.findById(id);
        return venta.map(saleMapper::toDto).orElse(null);
    }
}