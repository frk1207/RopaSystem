package com.inventario.sistema.reporte;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inventario.sistema.model.Inventario;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ReportePorCategoria implements ReporteInventario {
    @Override
    public void generar(List<Inventario> inventarios, String outputPath) {
        try {
            Map<String, Integer> stockPorCategoria = new HashMap<>();
            for (Inventario inv : inventarios) {
                String nombre = inv.getCategoria().getNombre();
                int cantidad = inv.getTipo().equalsIgnoreCase("Ingreso") ? inv.getCantidad() : -inv.getCantidad();
                stockPorCategoria.merge(nombre, cantidad, Integer::sum);
            }

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();
            document.add(new Paragraph("Reporte por Categoría\n\n"));
            for (Map.Entry<String, Integer> entry : stockPorCategoria.entrySet()) {
                document.add(new Paragraph("Categoría: " + entry.getKey() + " - Stock: " + entry.getValue()));
            }
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
