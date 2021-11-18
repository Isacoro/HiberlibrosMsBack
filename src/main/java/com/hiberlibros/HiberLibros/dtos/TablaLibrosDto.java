package com.hiberlibros.HiberLibros.dtos;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TablaLibrosDto implements Serializable {

    private Integer id;
    private Integer id_libro;
    private String uriPortada;
    private String isbn;
    private String titulo;
    private String autor;
    private String idioma;
    private String editorial;
    private Double valoracion;
    private String estado;
    private String propietario;
}
