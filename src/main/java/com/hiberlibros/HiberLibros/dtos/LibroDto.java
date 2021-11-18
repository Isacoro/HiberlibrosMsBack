package com.hiberlibros.HiberLibros.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroDto implements Serializable{

    private String titulo;
    private String uriPortada;
    private Double valoracionLibro;
    
}
