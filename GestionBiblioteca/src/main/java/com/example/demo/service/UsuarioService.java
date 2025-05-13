package com.example.demo.service;

import com.example.demo.recursos.Usuario;
import java.util.*;

public interface UsuarioService {
    Usuario buscarPorId(Long id);
    List<Usuario> obtenerTodos();
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    Usuario actualizar(Long id, Usuario usuario);
}