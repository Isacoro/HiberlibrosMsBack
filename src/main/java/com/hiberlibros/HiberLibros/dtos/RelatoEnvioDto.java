package com.hiberlibros.HiberLibros.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatoEnvioDto {

    private String titulo;
    private Integer idGenero;
    private Integer idUsuarioRelato;
    private String email;
    
}
