package com.example.demo.service;
import com.example.demo.enums.EstadoLibro;
import com.example.demo.exception.PrestamoNoEncontradoException;
import com.example.demo.recursos.Prestamo;
import com.example.demo.recursos.Usuario;
import com.example.demo.recursos.Libro;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.demo.repository.PrestamoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrestamoServiceTest {

    @Mock
    private PrestamoRepository repo;

    @InjectMocks
    private PrestamoServiceImpl service;

    private final Libro libro = new Libro(1L, "X", "T", "A", EstadoLibro.DISPONIBLE);
    private final Usuario usuario = new Usuario(1L, "U", "u@mail", "ACTIVO");
    private final LocalDate hoy = LocalDate.now();

    @Test
    void buscarPorId_existente() {
        Prestamo p = new Prestamo(10L, libro, usuario, hoy, hoy.plusDays(1));
        when(repo.findById(10L)).thenReturn(Optional.of(p));

        Prestamo res = service.buscarPorId(10L);
        assertEquals(10L, res.getId());
        verify(repo).findById(10L);
    }

    @Test
    void buscarPorId_noExistente_lanza() {
        when(repo.findById(5L)).thenReturn(Optional.empty());
        assertThrows(PrestamoNoEncontradoException.class, () ->
                service.buscarPorId(5L)
        );
    }

    @Test
    void obtenerTodos_devuelveLista() {
        Prestamo p1 = new Prestamo(1L, libro, usuario, hoy, hoy.plusDays(1));
        Prestamo p2 = new Prestamo(2L, libro, usuario, hoy, hoy.plusDays(2));
        when(repo.findAll()).thenReturn(List.of(p1, p2));

        List<Prestamo> res = service.obtenerTodos();
        assertEquals(2, res.size());
        verify(repo).findAll();
    }

    @Test
    void guardar_nuevoPrestamo_delegaEnRepo() {
        Prestamo sinId = new Prestamo(null, libro, usuario, hoy, hoy);
        Prestamo conId = new Prestamo(3L, libro, usuario, hoy, hoy);
        when(repo.save(sinId)).thenReturn(conId);

        Prestamo saved = service.guardar(sinId);
        assertEquals(3L, saved.getId());
        verify(repo).save(sinId);
    }

    @Test
    void eliminar_llamaAlRepo() {
        doNothing().when(repo).deleteById(4L);
        service.eliminar(4L);
        verify(repo).deleteById(4L);
    }

    @Test
    void actualizar_existente_actualizaYDevuelve() {
        Prestamo p = new Prestamo(6L, libro, usuario, hoy, hoy.plusDays(3));
        when(repo.existsById(6L)).thenReturn(true);
        when(repo.save(p)).thenReturn(p);

        Prestamo res = service.actualizar(6L, p);
        assertEquals(6L, res.getId());
        verify(repo).existsById(6L);
        verify(repo).save(p);
    }

    @Test
    void actualizar_noExistente_lanzaExcepcion() {
        Prestamo p = new Prestamo(null, libro, usuario, hoy, hoy.plusDays(1));
        when(repo.existsById(99L)).thenReturn(false);
        assertThrows(PrestamoNoEncontradoException.class, () ->
                service.actualizar(99L, p)
        );
    }
}
