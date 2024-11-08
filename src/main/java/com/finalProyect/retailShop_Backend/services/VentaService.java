package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.DTO.VentaDto;
import com.finalProyect.retailShop_Backend.entities.CartEntity;
import com.finalProyect.retailShop_Backend.entities.VentaEntity;
import com.finalProyect.retailShop_Backend.repositories.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;

    @Transactional
    public VentaDto confirmarVenta(CartEntity carrito) {
        // Crear y mapear la entidad VentaEntity desde el carrito
        VentaEntity venta = VentaEntity.builder()
                .user(carrito.getUser())
                .products(carrito.getCartProducts())
                .total(carrito.getTotal())
                .date(carrito.getDate())
                .paymentMethod(carrito.getPaymentMethod())
                .build();

        // Guardar la venta en la base de datos
        venta = ventaRepository.save(venta);

        // Transformar VentaEntity a VentaDto y retornar
        return new VentaDto(
                venta.getId(),
                venta.getUser().getId(),
                venta.getProducts(),
                venta.getTotal(),
                venta.getDate(),
                venta.getPaymentMethod()
        );
    }
}
