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
        // 1. Obtener el usuario (empleado) basado en el ID
        UserEntity user = userRepository.findById(saleDto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        // 2. Validar y mapear productos desde el DTO
        List<CartProductEntity> cartProductEntities = saleDto.getProducts().stream()
                .map(productDto -> {
                    ProductEntity productEntity = productRepository.findById(productDto.getId())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productDto.getId()));

                    // Crear instancia de CartProductEntity
                    return CartProductEntity.builder()
                            .product(productEntity)
                            .quantity(productDto.getQuantity())
                            .unitPrice(productDto.getUnitPrice())
                            .subTotal(productDto.getSubTotal())
                            .build();
                }).collect(Collectors.toList());

        // 3. Crear y persistir la venta
        SaleEntity saleEntity = SaleEntity.builder()
                .user(user)
                .clientId(saleDto.getClientId())
                .total(saleDto.getTotal())
                .date(LocalDate.now().toString())
                .paymentMethod(saleDto.getPaymentMethod())
                .build();

        // Asociar los productos a la venta (sincronización bidireccional)
        cartProductEntities.forEach(cartProductEntity -> cartProductEntity.setSale(saleEntity));
        saleEntity.setProducts(cartProductEntities);

        // Guardar la venta y productos asociados
        SaleEntity savedSale = saleRepository.save(saleEntity);

        // 4. Retornar como DTO
        return saleMapper.toDto(savedSale);
    }


    public List<SaleDto> obtenerVentas() {
        List<SaleEntity> ventas;

//        if (clientId.isPresent()) {
//            // Si clientId está presente, filtramos por ese valor
//            ventas = saleRepository.findByClientId(clientId.get());
//        } else {
            // Si no hay filtro, traemos todas las ventas
            ventas = saleRepository.findAll();
//        }

        return ventas.stream()
                .map(saleMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SaleDto> obtenerVentaPorId(Long id) {
        List<SaleEntity> venta = saleRepository.findByClientId(id);
        return venta.stream().map(saleMapper::toDto).collect(Collectors.toList());
    }

    public SaleDto obtenerVentaPorIdVenta(Long id) {
        Optional<SaleEntity> venta = saleRepository.findById(id);

        return saleMapper.toDto(
                venta.get()
        )
        ;
    }
}