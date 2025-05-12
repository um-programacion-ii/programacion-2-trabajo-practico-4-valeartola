package com.example.demo.repository;

import com.example.demo.recursos.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByNombre(String nombre);
    List<Usuario> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}