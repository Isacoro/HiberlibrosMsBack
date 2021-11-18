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

@Service
public class ForoLibroService implements IForoLibroService {

    @Autowired
    private ForoLibroRepository foroLibroRepository;
    @Autowired
    private IComentarioForoService comentarioForoService;

    @Override
    public List<ForoLibro> recuperarForosDeLibro(Libro idLibro) {
        return foroLibroRepository.findByIdLibroAndDesactivado(idLibro, Boolean.FALSE);
    }

    @Override
    public List<ForoLibro> recuperarTodosLosForos() {
        return foroLibroRepository.findByDesactivado(Boolean.FALSE);
    }

    @Override
    public void altaForoLibro(ForoLibro l) {
        l.setDesactivado(Boolean.FALSE);
        foroLibroRepository.save(l);
    }

    @Override
    public void eliminarForoLibro(Integer id) {
        ForoLibro fl = foroLibroRepository.findById(id).get();
        comentarioForoService.eliminarComentariosForo(fl);
        foroLibroRepository.deleteById(id);
    }

    @Override
    public void bajaForoLibro(Integer id) {
        ForoLibro fl = foroLibroRepository.findById(id).get();
        fl.setDesactivado(Boolean.TRUE);
        foroLibroRepository.save(fl);
    }

    @Override
    public ForoLibro consultarForo(Integer idForo) {
        Optional<ForoLibro> foroLibro =  foroLibroRepository.findById(idForo);
        if (foroLibro.isPresent()){
            return foroLibro.get();  
        }else {
            return new ForoLibro();
        }
    }
}
