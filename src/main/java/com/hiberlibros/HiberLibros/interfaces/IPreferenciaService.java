package com.hiberlibros.HiberLibros.interfaces;

import com.hiberlibros.HiberLibros.entities.Genero;
import com.hiberlibros.HiberLibros.entities.Preferencia;
import com.hiberlibros.HiberLibros.entities.Usuario;
import java.util.List;


public interface IPreferenciaService {

    List<Preferencia> findAll();

    public List<Preferencia> findByUsuario(Usuario usuario);
    
    public List<Preferencia> encontrarPorGenero(Genero g);

    public void addPreferencia(Preferencia preferencia);

    public void borrarPreferencia(Integer id);
    
    public void borrarPorGenero(Genero g);

}
