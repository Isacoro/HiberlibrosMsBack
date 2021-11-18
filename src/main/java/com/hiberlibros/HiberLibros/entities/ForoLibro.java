package com.hiberlibros.HiberLibros.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "foros_libros")
public class ForoLibro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // id propio
    
    private Boolean desactivado; //desactivado / activado
    
    private String  tituloForo; // titulo del foro
    
    @ManyToOne//pk libro
    @JoinColumn(name= "id_libro")
    private Libro   idLibro;   // libro del que es el foro
    
    @ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuario usuarioCreador;  //id Usuario creador del hilo

    @JsonBackReference
    @OneToMany(mappedBy = "id" ,fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ComentarioForo> comentarios; //usuario que genera el foro
    
}
