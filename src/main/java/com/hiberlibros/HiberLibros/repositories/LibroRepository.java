package com.hiberlibros.HiberLibros.repositories;

import com.hiberlibros.HiberLibros.entities.Autor;
import com.hiberlibros.HiberLibros.entities.Editorial;
import com.hiberlibros.HiberLibros.entities.Genero;
import com.hiberlibros.HiberLibros.entities.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Mohamad
 */
public interface LibroRepository extends JpaRepository<Libro, Integer> {

    public List<Libro> findByDesactivadoAndIsbnContainsIgnoreCaseOrTituloContainsIgnoreCase(Boolean b,String isbn, String titulo);
    
    public List<Libro> findByDesactivado(Boolean b); 

    public List<Libro> findByAutor(Autor autor);
    
    public List<Libro> findByAutorAndDesactivado(Autor autor, Boolean b);
    
    public List<Libro> findByGenero(Genero g);
    
    public List<Libro> findByEditorial(Editorial e);
}
