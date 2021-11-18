package com.hiberlibros.HiberLibros.interfaces;

import com.hiberlibros.HiberLibros.entities.Relato;
import com.hiberlibros.HiberLibros.entities.Usuario;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Usuario
 */
public interface IRelatoService {
    
    public void guardarRelato(String RUTA_BASE, Relato relato, MultipartFile ficherosubido, Integer id);

    public List<Relato> buscarRelato(String titulo);

    public List<Relato> todos();

    public List<Relato> buscarPorValoracionMayorAMenor();

    public List<Relato> buscarPorValoracionMenorAMayor();
    
    public List<Relato> encontrarPorAutor(Usuario u);
}
