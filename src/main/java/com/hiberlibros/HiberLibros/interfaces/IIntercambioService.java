package com.hiberlibros.HiberLibros.interfaces;

import com.hiberlibros.HiberLibros.entities.Intercambio;
import com.hiberlibros.HiberLibros.entities.UsuarioLibro;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface IIntercambioService {

    public void guardarIntercambio(UsuarioLibro ul_prestatario, UsuarioLibro ul_prestador);

    public List<Intercambio> encontrarULPrestador(List<UsuarioLibro> ul);

    public List<Intercambio> encontrarULPrestatario(List<UsuarioLibro> ul);

    public void finIntercambio(Integer id);
    
    public Boolean intercambioPendienteFinalizar(UsuarioLibro ul);
    
    public Integer contarIntercambiosPendientes(List<UsuarioLibro> ul);
    

}
