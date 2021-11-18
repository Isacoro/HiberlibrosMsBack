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

/**
 *
 * @author Isabel
 */
@Service
public class PreferenciaService implements IPreferenciaService {

    @Autowired
    private PreferenciaRepository prefRepo;
    @Autowired
    private UsuarioRepository usuRepo;

    @Override
    public List<Preferencia> findByUsuario(Usuario usuario) {
        return prefRepo.findByUsuario(usuario);
    }

    @Override
    public List<Preferencia> findAll() {
        return prefRepo.findAll();
    }

    @Override
    public void addPreferencia(Preferencia preferencia) {
        prefRepo.save(preferencia);
    }

    @Override
    public void borrarPreferencia(Integer id) {
        prefRepo.deleteById(id);
    }

    @Override
    public void borrarPorGenero(Genero g) {
        prefRepo.deleteByGenero(g);
    }

    @Override
    public List<Preferencia> encontrarPorGenero(Genero g) {
        return prefRepo.findByGenero(g);
    }
}
