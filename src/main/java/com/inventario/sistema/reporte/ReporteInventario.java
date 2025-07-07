package com.inventario.sistema.reporte;

import java.util.List;

import com.inventario.sistema.model.Inventario;

public interface ReporteInventario {
    void generar(List<Inventario> inventarios, String outputPath);
}
