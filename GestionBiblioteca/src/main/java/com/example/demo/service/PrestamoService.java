package com.example.demo.service;

import com.example.demo.recursos.Prestamo;

import java.util.*;

public interface PrestamoService {
    Prestamo buscarPorId(Long id);
    List<Prestamo> obtenerTodos();
    Prestamo guardar(Prestamo prestamo);
    void eliminar(Long id);
    Prestamo actualizar(Long id, Prestamo prestamo);
}