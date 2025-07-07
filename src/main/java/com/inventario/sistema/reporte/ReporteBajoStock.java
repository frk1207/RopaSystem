package com.inventario.sistema.reporte;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inventario.sistema.model.Inventario;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ReporteBajoStock implements ReporteInventario {
    @Override
    public void generar(List<Inventario> inventarios, String outputPath) {
        Map<String, Integer> stockPorProducto = new HashMap<>();
        for (Inventario inv : inventarios) {
            String nombre = inv.getProducto().getNombre();
            int cantidad = inv.getTipo().equalsIgnoreCase("Ingreso") ? inv.getCantidad() : -inv.getCantidad();
            stockPorProducto.merge(nombre, cantidad, Integer::sum);
        }
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();
            document.add(new Paragraph("Productos con bajo stock (< 10 unidades)\n\n"));
            for (Map.Entry<String, Integer> entry : stockPorProducto.entrySet()) {
                if (entry.getValue() < 10) {
                    document.add(new Paragraph(entry.getKey() + ": " + entry.getValue()));
                }
            }
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
