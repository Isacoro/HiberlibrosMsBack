package com.hiberlibros.HiberLibros.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatoDto {
    
    private Integer id;
    private String fichero;
    private GeneroDto genero;
    private String titulo;
    private Double valoracionUsuarios;
    private Integer numeroValoraciones;

    
}
