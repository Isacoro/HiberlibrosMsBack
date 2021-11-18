package com.hiberlibros.HiberLibros.dtos;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForoComentariosDto {
    
    ForoLibroDto foro;

    @JsonManagedReference
    List<ComentarioForoDto> comentarios;
}
