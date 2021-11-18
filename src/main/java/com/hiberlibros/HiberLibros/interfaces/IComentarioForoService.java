/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiberlibros.HiberLibros.interfaces;

import com.hiberlibros.HiberLibros.entities.ComentarioForo;
import com.hiberlibros.HiberLibros.entities.ForoLibro;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface IComentarioForoService {
    public void altaComentario(ComentarioForo comentario);
    public void bajaComentario(Integer idComentario);
    public void eliminarComentariosForo(ForoLibro fl);
    public List<ComentarioForo> consultarComentariosPorForo(ForoLibro foroLibro);
    
}
