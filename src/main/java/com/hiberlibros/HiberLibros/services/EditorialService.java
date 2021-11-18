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

/**
 *
 * @author Usuario
 */
@Service
public class EditorialService implements IEditorialService {

    @Autowired
    private EditorialRepository repoEditorial;

    @Autowired
    private ILibroService repoLibro;

    @Override
    public Editorial encontrarPorId(Integer id) {
        return repoEditorial.findByIdAndDesactivado(id,Boolean.FALSE).get();

    }

    @Override
    public List<Editorial> consultaTodas() {
        return repoEditorial.findByDesactivado(Boolean.FALSE);
    }

    @Override
    public void altaModificacionEditorial(Editorial editorial) {
        editorial.setDesactivado(Boolean.FALSE);
        repoEditorial.save(editorial);
    }

    @Override
    public Boolean bajaEditorial(Integer id) {
        Editorial e = encontrarPorId(id);
        List<Libro> l = repoLibro.encontrarPorEditorial(e);
        if (l.isEmpty() || l == null) {
            repoEditorial.deleteById(id);
            return true;
        } else {
            Boolean result = repoLibro.bajaLibrosList(l);
            if (result) {
                e.setDesactivado(Boolean.TRUE);
                repoEditorial.save(e);
            }
            return result;
        }
    }

    @Override
    public Editorial consultaPorIdEditorial(int idEditorial) {
        Optional<Editorial> editorial = repoEditorial.findByIdAndDesactivado(idEditorial,Boolean.FALSE);
        if (editorial.isPresent()) {
            return editorial.get();
        } else {
            return null;
        }
    }

    @Override
    public List<Editorial> consultaPorNombreEditorial(Editorial editorial) {
        return repoEditorial.findByNombreEditorialIgnoreCaseAndDesactivado(editorial.getNombreEditorial(),Boolean.FALSE);
    }

}
