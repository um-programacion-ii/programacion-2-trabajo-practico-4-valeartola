package com.example.demo.repository;

import com.example.demo.recursos.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioRepositoryTest {

    private UsuarioRepositoryImpl usuarioRepository;

    @BeforeEach
    void setUp() {
        usuarioRepository = new UsuarioRepositoryImpl();
    }

    @Test
    void saveUserAsignIdToUser() {
        Usuario usuario = new Usuario(1L, "Juan", "juan@mail.com", "Activo");
        Usuario guardado = usuarioRepository.save(usuario);

        assertNotNull(guardado.getId());
        assertEquals("Juan", guardado.getNombre());
        assertEquals("juan@mail.com", guardado.getEmail());
    }

    @Test
    void findByIdExistentReturnUser() {
        Usuario guardado = usuarioRepository.save(new Usuario(1L, "Ana", "ana@mail.com", "Activo"));
        Optional<Usuario> resultado = usuarioRepository.findById(guardado.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Ana", resultado.get().getNombre());
    }

    @Test
    void findByEmailExistentReturnUser() {
        usuarioRepository.save(new Usuario(1L, "Luis", "luis@mail.com","Activo"));
        Optional<Usuario> resultado = usuarioRepository.findByEmail("luis@mail.com");

        assertTrue(resultado.isPresent());
        assertEquals("Luis", resultado.get().getNombre());
    }

    @Test
    void findByEmailInexistentReturnEmpty() {
        Optional<Usuario> resultado = usuarioRepository.findByEmail("noexiste@mail.com");
        assertFalse(resultado.isPresent());
    }

    @Test
    void findByNameExistentReturnUser() {
        usuarioRepository.save(new Usuario(1L, "Carlos", "carlos@mail.com", "Activo"));
        Optional<Usuario> resultado = usuarioRepository.findByNombre("Carlos");

        assertTrue(resultado.isPresent());
        assertEquals("Carlos", resultado.get().getNombre());
    }

    @Test
    void findByNameInexistentReturnEmpty() {
        Optional<Usuario> resultado = usuarioRepository.findByNombre("Desconocido");
        assertFalse(resultado.isPresent());
    }

    @Test
    void deleteByIdDeleteUser() {
        Usuario guardado = usuarioRepository.save(new Usuario(1L, "Pepe", "pepe@mail.com", "Activo"));
        usuarioRepository.deleteById(guardado.getId());

        assertFalse(usuarioRepository.findById(guardado.getId()).isPresent());
    }


    @Test
    void existsByIdReturnFalseIfUserDoesNotExist() {
        assertFalse(usuarioRepository.existsById(999L));
    }

    @Test
    void findAllReturnAllUsers() {
        usuarioRepository.save(new Usuario(1L, "A", "a@mail.com", "Activo"));
        usuarioRepository.save(new Usuario(2L, "B", "b@mail.com", "No Activo"));

        List<Usuario> usuarios = usuarioRepository.findAll();
        assertEquals(2, usuarios.size());
    }
}