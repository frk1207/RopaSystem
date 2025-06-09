package com.inventario.sistema.service;

import com.inventario.sistema.model.Inventario;

import java.util.List;

public interface InventarioService {
    List<Inventario> listarTodos();
    Inventario obtenerPorId(Long id);
    void guardar(Inventario inventario);
    void eliminar(Long id);
}
