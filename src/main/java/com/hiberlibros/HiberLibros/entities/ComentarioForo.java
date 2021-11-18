package com.hiberlibros.HiberLibros.entities;

import com.hiberlibros.HiberLibros.dtos.ForoLibroDto;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
@Entity
@Table(name = "comentarios_foros")
public class ComentarioForo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String  comentarioForo;
    
    @ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuario usuarioComentario;

    @ManyToOne()
    @JoinColumn(name = "id_foro_libro")
    private ForoLibro foroLibro;
}
