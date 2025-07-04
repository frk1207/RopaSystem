package com.inventario.sistema.controller;

import com.inventario.sistema.service.ReporteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/reportes")
public class ReporteWebController {

    private final ReporteService reporteService;

    public ReporteWebController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping
    public String listarReportes(Model model) {
        model.addAttribute("reportes", reporteService.listarTodos());
        return "almacen/reportes/list";
    }
}
