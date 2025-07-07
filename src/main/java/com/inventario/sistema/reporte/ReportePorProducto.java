package com.inventario.sistema.reporte;

import java.io.FileOutputStream;
import java.util.List;

import com.inventario.sistema.model.Inventario;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ReportePorProducto implements ReporteInventario {
    @Override
    public void generar(List<Inventario> inventarios, String outputPath) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();
            document.add(new Paragraph("Historial por Producto\n\n"));
            for (Inventario inv : inventarios) {
                String linea = String.format("%s | %s | %s | %d", inv.getFecha(),
                        inv.getProducto().getNombre(), inv.getTipo(), inv.getCantidad());
                document.add(new Paragraph(linea));
            }
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
