package com.hiberlibros.HiberLibros.services;

import com.hiberlibros.HiberLibros.entities.Editorial;
import com.hiberlibros.HiberLibros.entities.Libro;
import com.hiberlibros.HiberLibros.interfaces.IEditorialService;
import com.hiberlibros.HiberLibros.interfaces.ILibroService;
import com.hiberlibros.HiberLibros.repositories.EditorialRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EditorialService implements IEditorialService {

    @Autowired
    private EditorialRepository editorialRepository;
    @Autowired
    private ILibroService libroService;


    @Override
    public Editorial encontrarPorId(Integer id) {
        return editorialRepository.findByIdAndDesactivado(id,Boolean.FALSE).get();
    }

    @Override
    public List<Editorial> consultaTodas() {
        return editorialRepository.findByDesactivado(Boolean.FALSE);
    }

    @Override
    public void altaModificacionEditorial(Editorial editorial) {
        editorial.setDesactivado(Boolean.FALSE);
        editorialRepository.save(editorial);
    }

    @Override
    public Boolean bajaEditorial(Integer id) {
        Editorial e = encontrarPorId(id);
        List<Libro> l = libroService.encontrarPorEditorial(e);
        if (l.isEmpty() || l == null) {
            editorialRepository.deleteById(id);
            return true;
        } else {
            Boolean result = libroService.bajaLibrosList(l);
            if (result) {
                e.setDesactivado(Boolean.TRUE);
                editorialRepository.save(e);
            }
            return result;
        }
    }

    @Override
    public Editorial consultaPorIdEditorial(int idEditorial) {
        Optional<Editorial> editorial = editorialRepository.findByIdAndDesactivado(idEditorial,Boolean.FALSE);
        if (editorial.isPresent()) {
            return editorial.get();
        } else {
            return null;
        }
    }

    @Override
    public List<Editorial> consultaPorNombreEditorial(Editorial editorial) {
        return editorialRepository.findByNombreEditorialIgnoreCaseAndDesactivado(editorial.getNombreEditorial(),Boolean.FALSE);
    }
}
