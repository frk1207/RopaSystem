package com.inventario.sistema.controller;

import com.inventario.sistema.model.Categoria;
import com.inventario.sistema.model.Inventario;
import com.inventario.sistema.model.Producto;
import com.inventario.sistema.service.CategoriaService;
import com.inventario.sistema.service.InventarioService;
import com.inventario.sistema.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/almacen/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/list")
    public String listarInventario(Model model) {
        List<Inventario> inventarios = inventarioService.listarTodos();
        model.addAttribute("inventarios", inventarios);
        return "almacen/inventario/list";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        Inventario inventario = new Inventario();
        List<Producto> productos = productoService.listarProductos();
        List<Categoria> categorias = categoriaService.listarCategorias();

        model.addAttribute("inventario", inventario);
        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categorias);
        return "almacen/inventario/form";
    }

    @PostMapping("/guardar")
    public String guardarInventario(@ModelAttribute("inventario") Inventario inventario) {
        // Recuperar el producto desde su ID
        if (inventario.getProducto() != null && inventario.getProducto().getId() != null) {
            productoService.obtenerProductoPorId(inventario.getProducto().getId())
                    .ifPresent(inventario::setProducto); // Setea el objeto completo
        }

        inventarioService.guardar(inventario);
        return "redirect:/almacen/inventario/list";
    }



    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
        Inventario inventario = inventarioService.obtenerPorId(id);
        List<Producto> productos = productoService.listarProductos();
        List<Categoria> categorias = categoriaService.listarCategorias();

        model.addAttribute("inventario", inventario);
        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categorias);
        return "almacen/inventario/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarInventario(@PathVariable("id") Long id) {
        inventarioService.eliminar(id);
        return "redirect:/almacen/inventario/list";
    }
}
