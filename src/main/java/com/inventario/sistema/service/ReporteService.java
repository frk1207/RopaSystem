package com.inventario.sistema.service;

import com.inventario.sistema.model.Reporte;
import com.inventario.sistema.repository.ReporteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReporteService {

    private final ReporteRepository reporteRepository;

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
}
