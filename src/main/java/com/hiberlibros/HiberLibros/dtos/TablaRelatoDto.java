package com.hiberlibros.HiberLibros.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TablaRelatoDto {

    private String titulo;
    private Double valoracionUsuarios;
    private String genero;
    private Integer numeroValoraciones;
    private Integer id;

}
