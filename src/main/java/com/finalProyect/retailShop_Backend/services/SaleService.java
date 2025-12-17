package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.DTO.CartProductDto;
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


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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

        /*// 2. Validar y mapear productos desde el DTO
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
        */

        List<CartProductEntity> cartProductEntities = saleDto.getProducts().stream()
                .map(productDto -> {

                    ProductEntity productEntity = productRepository.findById(productDto.getId())
                            .orElseThrow(() -> new RuntimeException(
                                    "Producto no encontrado con ID: " + productDto.getId()
                            ));

                    if (productEntity.getStock() < productDto.getQuantity()) {
                        throw new RuntimeException(
                                "Stock insuficiente para el producto: " + productEntity.getName()
                        );
                    }
                    productEntity.setStock(
                            productEntity.getStock() - productDto.getQuantity()
                    );

                    productRepository.save(productEntity);

                    return CartProductEntity.builder()
                            .product(productEntity)
                            .quantity(productDto.getQuantity())
                            .unitPrice(productEntity.getPrice())
                            .subTotal(
                                    productEntity.getPrice()
                                            .multiply(BigDecimal.valueOf(productDto.getQuantity()))
                                            .setScale(2, RoundingMode.HALF_UP)
                            )
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



    // Método para generar el PDF de la factura
    public ResponseEntity<byte[]> generateSalePdf(SaleDto sale) throws DocumentException, DocumentException {
        // Crear un ByteArrayOutputStream para generar el PDF en memoria
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Crear un PdfDocument y PdfWriter
        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);

        document.open();

        // Set page size and margins
        document.setPageSize(PageSize.A4);
        document.setMargins(36, 36, 36, 36);

        // Título y contenido
        addTitle(document);
        addShopInfo(document);
        addFacturaInfo(document, sale);
        addProductsTable(document, sale.getProducts());
        addTotals(document, sale.getTotal());

        // Cerrar el documento
        document.close();

        // Configurar los encabezados de la respuesta HTTP para indicar que es un archivo PDF
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice_" + sale.getId() +  "_" + sale.getDate() + ".pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        // Retornar el PDF como un arreglo de bytes en la respuesta
        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);
    }

    // Método para añadir el título
    private void addTitle(Document document) throws DocumentException {
        Paragraph title = new Paragraph("Retail Shop");
        com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
    }

    // Método para añadir la información de la tienda
    private void addShopInfo(Document document) throws DocumentException {
        Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
        Paragraph info = new Paragraph("Retail Shop SRL\nCIF: B-70227519\n", infoFont);
        info.setAlignment(Element.ALIGN_CENTER);
        document.add(info);
    }

    // Método para añadir la información de la factura
// Método para añadir la información de la factura
    private void addFacturaInfo(Document document, SaleDto sale) throws DocumentException {
        // Añadir un párrafo vacío con espaciado antes de los detalles de la factura
        Paragraph espacio = new Paragraph();
        espacio.setSpacingBefore(20); // Ajusta este valor según el espaciado deseado
        document.add(espacio);

        // Información de la factura alineada a la izquierda
        Paragraph facturaInfoIzquierda = new Paragraph(
                "Invoice Nr: " + sale.getId() + "\nDate: " + sale.getDate(),
                FontFactory.getFont(FontFactory.HELVETICA, 10)
        );
        facturaInfoIzquierda.setAlignment(Element.ALIGN_LEFT);

        // Información del cliente alineada a la derecha
        Paragraph facturaInfoDerecha = new Paragraph(
                "Client ID: " + sale.getClientId() + "\nPayment Method: " + sale.getPaymentMethod(),
                FontFactory.getFont(FontFactory.HELVETICA, 10)
        );
        facturaInfoDerecha.setAlignment(Element.ALIGN_RIGHT);

        // Crear una tabla con una fila y dos columnas para alinear la información en la misma línea
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1, 1}); // Ancho de las columnas

        // Celda para la información alineada a la izquierda
        PdfPCell cellLeft = new PdfPCell();
        cellLeft.addElement(facturaInfoIzquierda);
        cellLeft.setBorder(Rectangle.NO_BORDER);
        cellLeft.setHorizontalAlignment(Element.ALIGN_LEFT);

        // Celda para la información alineada a la derecha
        PdfPCell cellRight = new PdfPCell();
        cellRight.addElement(facturaInfoDerecha);
        cellRight.setBorder(Rectangle.NO_BORDER);
        cellRight.setHorizontalAlignment(Element.ALIGN_RIGHT);

        // Añadir las celdas a la tabla
        table.addCell(cellLeft);
        table.addCell(cellRight);

        // Añadir la tabla al documento
        document.add(table);

        // Añadir un espacio después de la información de la factura
        document.add(new Paragraph("\n"));
    }



    // Método para añadir la tabla de productos
    private void addProductsTable(Document document, List<CartProductDto> products) throws DocumentException {
        PdfPTable table = new PdfPTable(4); // 4 columnas: Cantidad, Descripción, Total, SKU
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setSpacingAfter(10);

        // Añadir encabezados de la tabla
        PdfPCell header1 = new PdfPCell(new Phrase("SKU", FontFactory.getFont(FontFactory.HELVETICA, 8)));
        header1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header1);

        PdfPCell header2 = new PdfPCell(new Phrase("Description", FontFactory.getFont(FontFactory.HELVETICA, 8)));
        header2.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header2);

        PdfPCell header3 = new PdfPCell(new Phrase("Quantity", FontFactory.getFont(FontFactory.HELVETICA, 8)));
        header3.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header3);

        PdfPCell header4 = new PdfPCell(new Phrase("Sub Total", FontFactory.getFont(FontFactory.HELVETICA, 8)));
        header4.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header4);

        // Añadir productos y sus precios a la tabla
        for (CartProductDto product : products) {
            table.addCell(new PdfPCell(new Phrase(product.getSku(), FontFactory.getFont(FontFactory.HELVETICA, 8))));
            table.addCell(new PdfPCell(new Phrase(product.getDescription(), FontFactory.getFont(FontFactory.HELVETICA, 8))));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(product.getQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8))));
            table.addCell(new PdfPCell(new Phrase("$" + product.getUnitPrice(), FontFactory.getFont(FontFactory.HELVETICA, 8))));
        }

        // Añadir la tabla al documento
        document.add(table);
    }

    // Método para añadir los totales
    private void addTotals(Document document, BigDecimal total) throws DocumentException {
        BigDecimal baseIVA = total.multiply(BigDecimal.valueOf(0.21)); // IVA base 21%
        BigDecimal subTotal = total.subtract(baseIVA);

        // Redondear los valores a dos decimales
        baseIVA = baseIVA.setScale(2, RoundingMode.HALF_UP);
        subTotal = subTotal.setScale(2, RoundingMode.HALF_UP);

        // Añadir total
        Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
        Paragraph totalParagraph = new Paragraph(
                "IVA 21%: $" + baseIVA +
                        "\nSubtotal: $" + subTotal +
                        "\nTotal: $" + total.setScale(2, RoundingMode.HALF_UP),
                infoFont
        );
        totalParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(totalParagraph);
    }



}