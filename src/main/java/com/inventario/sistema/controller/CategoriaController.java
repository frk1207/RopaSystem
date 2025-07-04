package com.inventario.sistema.controller;

import com.inventario.sistema.model.Categoria;
import com.inventario.sistema.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listarCategorias(Model model) {
        model.addAttribute("categorias", categoriaService.listarCategorias());
        return "almacen/categorias/list";  // Aquí iría tu plantilla Thymeleaf lista de categorias
    }

    @GetMapping("/form")
    public String mostrarFormularioCategoria(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("categoria", new Categoria());
        } else {
            categoriaService.obtenerCategoriaPorId(id).ifPresentOrElse(
                categoria -> model.addAttribute("categoria", categoria),
                () -> model.addAttribute("categoria", new Categoria())
            );
        }
        return "almacen/categorias/form"; // Formulario para crear o editar
    }

    @PostMapping("/guardar")
    public String guardarCategoria(@ModelAttribute Categoria categoria) {
        categoriaService.guardarCategoria(categoria);
        return "redirect:/admin/categorias";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return "redirect:/admin/categorias";
    }
}
