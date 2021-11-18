/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiberlibros.HiberLibros.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hiberlibros.HiberLibros.entities.ComentarioForo;
import com.hiberlibros.HiberLibros.entities.Libro;
import com.hiberlibros.HiberLibros.entities.Usuario;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Usuario
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForoLibroDto {
     private Integer id; // id propio
    
    private Boolean desactivado; //desactivado / activado
    
    private String  tituloForo; // titulo del foro
   

    private Libro   idLibro;   // libro del que es el foro
    


    private Usuario usuarioCreador;  //id Usuario creador del hilo

    @JsonBackReference
    private List<ComentarioForo> comentarios; //usuario que genera el foro
}
