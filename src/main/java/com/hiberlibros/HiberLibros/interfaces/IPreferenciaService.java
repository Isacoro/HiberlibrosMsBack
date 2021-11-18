package com.hiberlibros.HiberLibros.interfaces;

import com.hiberlibros.HiberLibros.entities.Genero;
import com.hiberlibros.HiberLibros.entities.Preferencia;
import com.hiberlibros.HiberLibros.entities.Usuario;
import java.util.List;

/**
 *
 * @author Isabel
 */
public interface IPreferenciaService {

    //Todas las preferencias
    List<Preferencia> findAll();

    //Preferencias por usuario
    public List<Preferencia> findByUsuario(Usuario usuario);
    
    public List<Preferencia> encontrarPorGenero(Genero g);

    //Añadir preferencia
    public void addPreferencia(Preferencia preferencia);

    //Borrar preferencia
    public void borrarPreferencia(Integer id);
    
    public void borrarPorGenero(Genero g);

}
