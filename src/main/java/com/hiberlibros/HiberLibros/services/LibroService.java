package com.hiberlibros.HiberLibros.services;

import com.hiberlibros.HiberLibros.entities.Autor;
import com.hiberlibros.HiberLibros.entities.Editorial;
import com.hiberlibros.HiberLibros.entities.ForoLibro;
import com.hiberlibros.HiberLibros.entities.Genero;
import com.hiberlibros.HiberLibros.entities.Libro;
import com.hiberlibros.HiberLibros.interfaces.IForoLibroService;
import com.hiberlibros.HiberLibros.repositories.LibroRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hiberlibros.HiberLibros.interfaces.ILibroService;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioLibroService;

/**
 *
 * @author Usuario
 */
@Service
public class LibroService implements ILibroService {

    @Autowired
    private LibroRepository libroRep;

    @Autowired
    private IUsuarioLibroService serviceUL;

    @Autowired
    private IForoLibroService serviceForoLibro;

    @Override
    public List<Libro> buscarLibro(String libro) {//recibe un string y busca si hay coincidencias en isbn o libro
        return libroRep.findByDesactivadoAndIsbnContainsIgnoreCaseOrTituloContainsIgnoreCase(Boolean.FALSE, libro, libro);
    }

    @Override
    public Libro libroId(Integer id) {
        return libroRep.findById(id).get();
    }

    @Override
    public void guardarLibro(Libro l) {
        l.setDesactivado(Boolean.FALSE);
        libroRep.save(l);
    }

    @Override
    public void valorarLibro(Libro l, Integer valoracion) {
        if (valoracion != null) {
            if (l.getNumeroValoraciones() == null) {
                l.setNumeroValoraciones(0);
            }
            if (l.getValoracionLibro() == null) {
                l.setValoracionLibro(0.0);
            }

            l.setNumeroValoraciones(l.getNumeroValoraciones() + 1);
            double operacion = (l.getValoracionLibro() * (l.getNumeroValoraciones() - 1) + valoracion) / l.getNumeroValoraciones();
            double redondeo = Math.round(operacion * 100) / 100.0;
            l.setValoracionLibro(redondeo);
        
        guardarLibro(l);
        }

    }

    @Override
    public Integer contarLibros() {
        long numLibros = libroRep.findAll().stream()
                .count();
        return (int) (numLibros);
    }

    @Override
    public List<Libro> encontrarPorAutor(Autor a) {
        return libroRep.findByAutor(a);
    }

    @Override
    public Boolean bajaLibroId(Integer id) {
        Libro l = libroId(id);
        List<ForoLibro> fl = serviceForoLibro.recuperarForosDeLibro(l);
        if (serviceUL.libroBorrado(l)) {
            fl.forEach(x -> serviceForoLibro.bajaForoLibro(x.getId()));
            l.setDesactivado(Boolean.TRUE);
            libroRep.save(l);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Boolean bajaLibrosList(List<Libro> l) {
        Boolean result = serviceUL.librosOcupado(l);
        if (result) {//si es verdadero significa que no hay libros en intercambio por lo que se pueden desactivar
            l.forEach(x -> {
                bajaLibroId(x.getId());
            });
        }
        return result;//devuelve true=si ha podido realizar la acción (no había libros en intercambio) false si no ha podido=libros en intercambio

    }

    @Override
    public List<Libro> encontrarDisponible() {
        return libroRep.findByDesactivado(Boolean.FALSE);
    }

    @Override
    public List<Libro> encontrarPorGenero(Genero g) {
        return libroRep.findByGenero(g);
    }

    @Override
    public List<Libro> encontrarPorEditorial(Editorial e) {
        return libroRep.findByEditorial(e);
    }

    @Override
    public List<Libro> encontrarPorAutorActivos(Autor a) {
        return libroRep.findByAutorAndDesactivado(a, Boolean.FALSE);
    }

}
