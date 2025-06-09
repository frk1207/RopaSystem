package com.inventario.sistema.service;

import com.inventario.sistema.model.Inventario;
import com.inventario.sistema.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioServiceImpl implements InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Override
    public List<Inventario> listarTodos() {
        return inventarioRepository.findAll();
    }

    @Override
    public Inventario obtenerPorId(Long id) {
        Optional<Inventario> optional = inventarioRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void guardar(Inventario inventario) {
        inventarioRepository.save(inventario);
    }

    @Override
    public void eliminar(Long id) {
        inventarioRepository.deleteById(id);
    }
}
