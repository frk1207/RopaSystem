package com.inventario.sistema.controller;

import com.inventario.sistema.model.Producto;
import com.inventario.sistema.model.Reporte;
import com.inventario.sistema.service.ProductoService;
import com.inventario.sistema.service.ReporteService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // <-- Asegúrate de tener esto
import org.springframework.web.bind.annotation.GetMapping;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ReporteController {

    private final ProductoService productoService;
    private final ReporteService reporteService;

    public ReporteController(ProductoService productoService, ReporteService reporteService) {
        this.productoService = productoService;
        this.reporteService = reporteService;
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
            document.add(new Paragraph("Fecha: " + LocalDateTime.now()));
            document.add(new Paragraph(" "));

            for (Producto p : productos) {
                document.add(new Paragraph("ID: " + p.getId()));
                document.add(new Paragraph("Nombre: " + p.getNombre()));
                document.add(new Paragraph("Precio: " + p.getPrecio()));
                document.add(new Paragraph("Categoría: " + (p.getCategoria() != null ? p.getCategoria().getNombre() : "Sin categoría")));
                document.add(new Paragraph("---------------"));
            }

            document.close();

            String nombre = "Reporte Productos - " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            reporteService.guardar(nombre);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "reporte_productos.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(baos.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    // 👇 AGREGA este nuevo método para que funcione /almacen/reportes
    @GetMapping("/almacen/reportes")
    public String listarReportesParaAlmacenero(Model model) {
        List<Reporte> reportes = reporteService.listarTodos();
        model.addAttribute("reportes", reportes);
        return "almacen/reportes/list";
    }
}
