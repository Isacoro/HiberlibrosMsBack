/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiberlibros.HiberLibros.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hiberlibros.HiberLibros.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioForoDto {

    private Integer id;
    
    private String  comentarioForo;
    
   // private Usuario usuarioComentario;
    
  //  private ForoLibroDto foroLibro;
}
