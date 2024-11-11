package com.finalProyect.retailShop_Backend.DTO;



import com.finalProyect.retailShop_Backend.entities.products.CartProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


//TODO : AGREGAR CLIENTID(NUMERO DE DNI);
/*TODO: guiarse por estos tipos de dato:   export interface Sale{
    id: number;
    seller: string;
    client: number;
    products: cartProduct[];
    total: number;
    date: Date;
    paymentMethod: string;
  }
  export interface cartProduct {
    sku: number;
    cartId: number;
    description: string;
    quantity: number;
    unitPrice: number;
    subTotal: number;
  }
  
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDto {
    private Long id;
    private Long userId;  // ID del usuario que realiza la venta
    //En vez de guardar un cartProductEntity, guardar un CartProductDto con la data de mi CartProduct.
    private List<CartProductEntity> products;  // Lista de productos en la venta
    private BigDecimal total;  // Total de la venta
    private LocalDate date;  // Fecha de la venta
    private String paymentMethod;  // Método de pago
}
