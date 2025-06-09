package com.inventario.sistema.controller;

import com.inventario.sistema.model.Producto;
import com.inventario.sistema.service.ProductoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
public class ReporteController {

    private final ProductoService productoService;

    public ReporteController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/reporte/productos/pdf")
public ResponseEntity<byte[]> generarReporteProductosPDF() {
    try {
        List<Producto> productos = productoService.listarProductos();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        document.add(new Paragraph("Reporte de Productos"));
        document.add(new Paragraph(" "));

        for (Producto p : productos) {
            document.add(new Paragraph("ID: " + p.getId()));
            document.add(new Paragraph("Nombre: " + p.getNombre()));
            document.add(new Paragraph("Precio: " + p.getPrecio()));
            document.add(new Paragraph("Categoría: " + (p.getCategoria() != null ? p.getCategoria().getNombre() : "Sin categoría")));
            document.add(new Paragraph("---------------"));
        }

        document.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "reporte_productos.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(baos.toByteArray());

    } catch (Exception e) {
        return ResponseEntity.status(500).build();
    }
}

}
