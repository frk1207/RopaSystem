package com.inventario.sistema.reporte;

import java.io.FileOutputStream;
import java.util.List;

import com.inventario.sistema.model.Inventario;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ReporteGeneral implements ReporteInventario {
    @Override
    public void generar(List<Inventario> inventarios, String outputPath) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();
            document.add(new Paragraph("Reporte General de Inventario\n\n"));
            for (Inventario inv : inventarios) {
                String linea = String.format("Fecha: %s | Producto: %s | Tipo: %s | Cantidad: %d", inv.getFecha(),
                        inv.getProducto().getNombre(), inv.getTipo(), inv.getCantidad());
                document.add(new Paragraph(linea));
            }
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}