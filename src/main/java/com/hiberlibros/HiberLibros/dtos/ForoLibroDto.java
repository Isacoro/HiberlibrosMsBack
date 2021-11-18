package com.hiberlibros.HiberLibros.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hiberlibros.HiberLibros.entities.ComentarioForo;
import com.hiberlibros.HiberLibros.entities.Libro;
import com.hiberlibros.HiberLibros.entities.Usuario;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForoLibroDto {

    private Integer id;
    private Boolean desactivado;
    private String  tituloForo;
    private Libro   idLibro;
    private Usuario usuarioCreador;  //id Usuario creador del hilo

    @JsonBackReference
    private List<ComentarioForo> comentarios; //usuario que genera el foro
}
