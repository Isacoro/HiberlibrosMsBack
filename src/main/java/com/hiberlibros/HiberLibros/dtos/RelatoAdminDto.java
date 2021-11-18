package com.hiberlibros.HiberLibros.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatoAdminDto {
    
    private Integer id;
    private String fichero;
    private String titulo;
    private Double valoracionUsuarios;
    private Integer numeroValoraciones;
    private GeneroDto genero;

    
}
