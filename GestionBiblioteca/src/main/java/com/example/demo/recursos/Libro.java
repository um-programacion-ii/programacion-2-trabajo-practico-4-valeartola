package com.example.demo.recursos;
import com.example.demo.enums.EstadoLibro;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Libro {
    private Long id;
    private String isbn;
    private String titulo;
    private String autor;
    private EstadoLibro estado;
}