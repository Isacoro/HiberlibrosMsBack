package com.hiberlibros.HiberLibros.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAutor;
    @Column
    private String nombre;
    @Column
    private String apellidos;
    @Column
    private String biografia;

    private Boolean desactivado;
    @JsonBackReference
    @OneToMany(mappedBy = "autor")
    private List<Libro> libros;

//    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)//si  se borra autor, se borra relacion de autor_libro
//    private List<AutorLibro> autorLibros;
//    @Override
//    public String toString() {
//        return ""+nombre + " " + apellidos;
//    }
}
