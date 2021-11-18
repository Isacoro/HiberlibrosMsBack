package com.hiberlibros.HiberLibros.interfaces;

import com.hiberlibros.HiberLibros.entities.ComentarioForo;
import com.hiberlibros.HiberLibros.entities.ForoLibro;
import java.util.List;


public interface IComentarioForoService {

    public void altaComentario(ComentarioForo comentario);

    public void bajaComentario(Integer idComentario);

    public void eliminarComentariosForo(ForoLibro fl);

    public List<ComentarioForo> consultarComentariosPorForo(ForoLibro foroLibro);
    
}
