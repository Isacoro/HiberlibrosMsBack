package com.hiberlibros.HiberLibros.services;

import com.hiberlibros.HiberLibros.entities.Genero;
import com.hiberlibros.HiberLibros.entities.Preferencia;
import com.hiberlibros.HiberLibros.entities.Usuario;
import com.hiberlibros.HiberLibros.repositories.PreferenciaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hiberlibros.HiberLibros.interfaces.IPreferenciaService;
import com.hiberlibros.HiberLibros.repositories.UsuarioRepository;


@Service
public class PreferenciaService implements IPreferenciaService {

    @Autowired
    private PreferenciaRepository preferenciaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public List<Preferencia> findByUsuario(Usuario usuario) {
        return preferenciaRepository.findByUsuario(usuario);
    }

    @Override
    public List<Preferencia> findAll() {
        return preferenciaRepository.findAll();
    }

    @Override
    public void addPreferencia(Preferencia preferencia) {
        preferenciaRepository.save(preferencia);
    }

    @Override
    public void borrarPreferencia(Integer id) {
        preferenciaRepository.deleteById(id);
    }

    @Override
    public void borrarPorGenero(Genero g) {
        preferenciaRepository.deleteByGenero(g);
    }

    @Override
    public List<Preferencia> encontrarPorGenero(Genero g) {
        return preferenciaRepository.findByGenero(g);
    }
}
