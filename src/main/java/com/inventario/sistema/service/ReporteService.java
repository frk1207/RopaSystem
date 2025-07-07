package com.inventario.sistema.service;

import com.inventario.sistema.model.Inventario;
import com.inventario.sistema.model.Reporte;
import com.inventario.sistema.reporte.ReporteFactory;
import com.inventario.sistema.reporte.ReporteInventario;
import com.inventario.sistema.repository.InventarioRepository;
import com.inventario.sistema.repository.ReporteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReporteService {

    private final ReporteRepository reporteRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    public ReporteService(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    public List<Reporte> listarTodos() {
        return reporteRepository.findAll();
    }

    public Reporte guardar(String nombre) {
        Reporte r = new Reporte();
        r.setNombre(nombre);
        r.setFecha(LocalDateTime.now());
        return reporteRepository.save(r);
    }
    

    public Reporte buscarPorId(Long id) {
        return reporteRepository.findById(id).orElse(null);
    }

    public void generarReporte(String tipo) {
        List<Inventario> inventarios = inventarioRepository.findAll();
        ReporteInventario reporte = ReporteFactory.crearReporte(tipo);
        String outputPath = "reporte_" + tipo + ".pdf";
        reporte.generar(inventarios, outputPath);
    }
}

