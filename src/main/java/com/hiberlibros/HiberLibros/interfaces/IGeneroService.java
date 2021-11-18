package com.hiberlibros.HiberLibros.interfaces;

import com.hiberlibros.HiberLibros.entities.Genero;
import java.util.List;

/**
 *
 * @author Isabel
 */
public interface IGeneroService {
    
    public Genero encontrarPorId(Integer id);

    public void guardarGenero(Genero genero);

    public Boolean borrarGenero(Integer id);

    public List<Genero> getGeneros();

}
