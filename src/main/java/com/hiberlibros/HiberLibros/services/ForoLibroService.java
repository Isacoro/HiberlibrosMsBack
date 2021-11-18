package com.hiberlibros.HiberLibros.services;

import com.hiberlibros.HiberLibros.entities.ForoLibro;
import com.hiberlibros.HiberLibros.entities.Libro;
import com.hiberlibros.HiberLibros.interfaces.IComentarioForoService;
import com.hiberlibros.HiberLibros.interfaces.IForoLibroService;
import com.hiberlibros.HiberLibros.repositories.ForoLibroRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class ForoLibroService implements IForoLibroService {

    @Autowired
    private ForoLibroRepository repoForoLibro;
    @Autowired
    private IComentarioForoService serviceComentForo;

    @Override
    public List<ForoLibro> recuperarForosDeLibro(Libro idLibro) {
        return repoForoLibro.findByIdLibroAndDesactivado(idLibro, Boolean.FALSE);
    }

    @Override
    public List<ForoLibro> recuperarTodosLosForos() {
        return repoForoLibro.findByDesactivado(Boolean.FALSE);
    }

    @Override
    public void altaForoLibro(ForoLibro l) {
        l.setDesactivado(Boolean.FALSE);
        repoForoLibro.save(l);
    }

    @Override
    public void eliminarForoLibro(Integer id) {
        ForoLibro fl = repoForoLibro.findById(id).get();
        serviceComentForo.eliminarComentariosForo(fl);
        repoForoLibro.deleteById(id);
    }

    @Override
    public void bajaForoLibro(Integer id) {
        ForoLibro fl = repoForoLibro.findById(id).get();
        fl.setDesactivado(Boolean.TRUE);
        repoForoLibro.save(fl);
    }

    @Override
    public ForoLibro consultarForo(Integer idForo) {
        Optional<ForoLibro> foroLibro =  repoForoLibro.findById(idForo);
        if (foroLibro.isPresent()){
            return foroLibro.get();  
        }else {
            return new ForoLibro();
        }
        
    }
    
    
    
}
