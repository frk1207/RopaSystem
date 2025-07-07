package com.inventario.sistema.reporte;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inventario.sistema.model.Inventario;

public class ReporteStockActual implements ReporteInventario {

    @Override
    public void generar(List<Inventario> inventarios, String outputPath) {
        try {
            Map<String, Integer> stockPorProducto = new HashMap<>();

            for (Inventario inv : inventarios) {
                String nombre = inv.getProducto().getNombre();
                int cantidad = inv.getCantidad();
                if ("Ingreso".equalsIgnoreCase(inv.getTipo())) {
                    stockPorProducto.merge(nombre, cantidad, Integer::sum);
                } else if ("Salida".equalsIgnoreCase(inv.getTipo())) {
                    stockPorProducto.merge(nombre, -cantidad, Integer::sum);
                }
            }

            // Crear PDF con OpenPDF
            com.lowagie.text.Document document = new com.lowagie.text.Document();
            com.lowagie.text.pdf.PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();

            document.add(new com.lowagie.text.Paragraph("📦 Reporte de Stock Actual\n\n"));

            for (Map.Entry<String, Integer> entry : stockPorProducto.entrySet()) {
                document.add(new com.lowagie.text.Paragraph(
                    "Producto: " + entry.getKey() + " - Stock: " + entry.getValue()
                ));
            }

            document.close();

            System.out.println("PDF generado: " + outputPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}