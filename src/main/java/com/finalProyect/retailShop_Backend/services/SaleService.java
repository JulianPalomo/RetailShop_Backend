package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.DTO.SaleDto;
import com.finalProyect.retailShop_Backend.entities.SaleEntity;
import com.finalProyect.retailShop_Backend.repositories.SaleRepository;
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
    private final StockService stockService;

    // Confirmar venta
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


    // Método auxiliar para mapear SaleEntity a SaleDto
    private SaleDto mapearADto(SaleEntity saleEntity) {
        return new SaleDto(
                saleEntity.getId(),
                saleEntity.getUser().getId(),
                saleEntity.getProducts(),
                saleEntity.getTotal(),
                saleEntity.getDate(),
                saleEntity.getPaymentMethod()
        );
    }
}
