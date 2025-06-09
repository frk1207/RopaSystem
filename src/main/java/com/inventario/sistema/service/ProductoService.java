package com.inventario.sistema.service;

import com.inventario.sistema.model.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> listarProductos();
    Optional<Producto> obtenerProductoPorId(Long id);
    Producto guardar(Producto producto);
    void eliminarProducto(Long id);
}
