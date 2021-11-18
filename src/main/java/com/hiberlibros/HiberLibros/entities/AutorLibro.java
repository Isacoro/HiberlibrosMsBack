package com.hiberlibros.HiberLibros.entities;

import javax.persistence.CascadeType;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "autor_libro") //Tabla intermedia de autor y libro
public class AutorLibro {
	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	  	
            @ManyToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name = "id_autor")
	    private Autor autor;
	    
            @ManyToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name = "id_libro")
	    private Libro libro;
}
