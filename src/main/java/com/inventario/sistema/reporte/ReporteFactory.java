package com.inventario.sistema.reporte;

public class ReporteFactory {
     public static ReporteInventario crearReporte(String tipo) {
        return switch (tipo.toLowerCase()) {
            case "general" -> new ReporteGeneral();
            case "stock" -> new ReporteStockActual();
            case "fecha" -> new ReportePorFecha();
            case "categoria" -> new ReportePorCategoria();
            case "bajo-stock" -> new ReporteBajoStock();
            case "producto" -> new ReportePorProducto();
            default -> throw new IllegalArgumentException("Tipo de reporte no válido");
        };
    }
}