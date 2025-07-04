package com.inventario.sistema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.inventario.sistema.service.CategoriaService;
import com.inventario.sistema.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

@Controller
public class AlmacenController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/almacen/productos/form")
    public String mostrarFormularioProducto(Model model) {
        model.addAttribute("categorias", categoriaService.listarCategorias());
        model.addAttribute("producto", new Producto());
        return "almacen/productos/form";  // Cambia el path si tu formulario tiene otro nombre o carpeta
    }

    @GetMapping("/almacen/inicio")
    public String inicioAlmacen() {
        return "almacen/inicio";
    }
}

