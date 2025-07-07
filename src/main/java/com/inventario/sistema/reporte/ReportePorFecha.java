package com.inventario.sistema.reporte;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.inventario.sistema.model.Inventario;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ReportePorFecha implements ReporteInventario {
    @Override
    public void generar(List<Inventario> inventarios, String outputPath) {
        try {
            Map<LocalDate, Integer> movimientosPorFecha = new TreeMap<>();
            for (Inventario inv : inventarios) {
                int cantidad = inv.getTipo().equalsIgnoreCase("Ingreso") ? inv.getCantidad() : -inv.getCantidad();
                movimientosPorFecha.merge(inv.getFecha(), cantidad, Integer::sum);
            }

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();
            document.add(new Paragraph("Reporte por Fecha\n\n"));
            for (Map.Entry<LocalDate, Integer> entry : movimientosPorFecha.entrySet()) {
                document.add(new Paragraph(entry.getKey() + ": " + entry.getValue()));
            }
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

