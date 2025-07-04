package com.inventario.sistema.controller;

import com.inventario.sistema.model.Categoria;
import com.inventario.sistema.model.Producto;
import com.inventario.sistema.service.CategoriaService;
import com.inventario.sistema.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listarProductos(Model model) {
        List<Producto> productos = productoService.listarProductos();
        model.addAttribute("productos", productos);
        return "almacen/productos/list";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        Producto producto = new Producto();
        List<Categoria> categorias = categoriaService.listarCategorias();
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categorias);
        return "productos/form";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute("producto") Producto producto) {
        productoService.guardar(producto);
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
        Optional<Producto> producto = productoService.obtenerProductoPorId(id);
        List<Categoria> categorias = categoriaService.listarCategorias();
        model.addAttribute("producto", producto.orElse(null));
        model.addAttribute("categorias", categorias);
        return "productos/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Long id) {
        productoService.eliminarProducto(id);
        return "redirect:/productos";
    }
}
