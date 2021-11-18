package com.hiberlibros.HiberLibros.interfaces;

import com.hiberlibros.HiberLibros.entities.ForoLibro;
import com.hiberlibros.HiberLibros.entities.Libro;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface IForoLibroService {

    public List<ForoLibro> recuperarForosDeLibro(Libro idLibro);

    public List<ForoLibro> recuperarTodosLosForos();

    public void altaForoLibro(ForoLibro l);
    public void eliminarForoLibro (Integer id);
    public void bajaForoLibro(Integer id);
    public ForoLibro consultarForo(Integer idForo);
}
