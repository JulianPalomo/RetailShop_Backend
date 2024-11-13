package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.DTO.SaleDto;
import com.finalProyect.retailShop_Backend.entities.*;

import com.finalProyect.retailShop_Backend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SaleService {

    @Autowired
    private final SaleRepository saleRepository;


    @Transactional
    public SaleDto confirmarVenta(SaleDto saleDto) {
        SaleEntity saleEntity = new SaleEntity();
        // Asigna los campos, descontar stock, y luego guarda la venta
        // ...

        SaleEntity savedSale = saleRepository.save(saleEntity);
        return mapearADto(savedSale);
    }

    // Obtener todas las ventas
    public List<SaleDto> obtenerTodasLasVentas() {
        return saleRepository.findAll().stream().map(this::mapearADto).collect(Collectors.toList());
    }

    // Obtener una venta por ID
    public SaleDto obtenerVentaPorId(Long id) {
        Optional<SaleEntity> venta = saleRepository.findById(id);
        return venta.map(this::mapearADto).orElse(null);
    }

    // Eliminar una venta por ID
    public boolean eliminarVentaPorId(Long id) {
        if (saleRepository.existsById(id)) {
            saleRepository.deleteById(id);
            return true;
        }
        return false;
    }

}