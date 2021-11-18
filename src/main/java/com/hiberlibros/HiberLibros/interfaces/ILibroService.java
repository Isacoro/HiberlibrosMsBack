package com.hiberlibros.HiberLibros.interfaces;

import com.hiberlibros.HiberLibros.entities.Autor;
import com.hiberlibros.HiberLibros.entities.Editorial;
import com.hiberlibros.HiberLibros.entities.Genero;
import com.hiberlibros.HiberLibros.entities.Libro;
import java.util.List;


public interface ILibroService {

    public List<Libro> buscarLibro(String libro);
    
    public List<Libro> encontrarDisponible();

    public Libro libroId(Integer id);

    public void guardarLibro(Libro l);

    public Integer contarLibros();

    public List<Libro> encontrarPorAutor(Autor a);
    
    public List<Libro> encontrarPorAutorActivos(Autor a);

    public void valorarLibro(Libro l, Integer valoracion);
    
    public Boolean bajaLibroId(Integer id);
    
    public Boolean bajaLibrosList(List<Libro> l);
    
    public List<Libro> encontrarPorGenero(Genero g);
    
    public List<Libro> encontrarPorEditorial(Editorial e);
}
