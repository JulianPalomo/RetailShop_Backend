package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.DTO.SaleDto;
import com.finalProyect.retailShop_Backend.entities.SaleEntity;
import com.finalProyect.retailShop_Backend.entities.persons.UserEntity;
import com.finalProyect.retailShop_Backend.entities.products.CartProductEntity;
import com.finalProyect.retailShop_Backend.mappers.SaleMapper;
import com.finalProyect.retailShop_Backend.repositories.CartProductRepository;
import com.finalProyect.retailShop_Backend.repositories.SaleRepository;
import com.finalProyect.retailShop_Backend.repositories.UserRepository;
import com.finalProyect.retailShop_Backend.DTO.CartProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;
    private final UserRepository userRepository; // Inyectamos UserRepository
    private final CartProductRepository cartProductRepository; // Inyectamos CartProductRepository

    @Transactional
    public SaleDto confirmarVenta(SaleDto saleDto) {
        // Obtener el usuario basado en saleDto.getUserId()
        UserEntity user = userRepository.findById(saleDto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener los productos del carrito asociados a la venta
        List<CartProductEntity> cartProducts = cartProductRepository.findAllById(
                saleDto.getProducts().stream()
                        .map(CartProductDto::getSku)
                        .collect(Collectors.toList())
        );

        // Llamar a toEntity con todos los parámetros necesarios
        SaleEntity saleEntity = saleMapper.toEntity(saleDto, user, cartProducts);
        SaleEntity savedSale = saleRepository.save(saleEntity);
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

    public boolean eliminarVentaPorId(Long id) {
        if (saleRepository.existsById(id)) {
            saleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}