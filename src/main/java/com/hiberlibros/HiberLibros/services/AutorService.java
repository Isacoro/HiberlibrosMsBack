package com.hiberlibros.HiberLibros.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiberlibros.HiberLibros.dtos.LibroDto;
import com.hiberlibros.HiberLibros.entities.Autor;
import com.hiberlibros.HiberLibros.entities.Libro;
import com.hiberlibros.HiberLibros.interfaces.IAutorService;
import com.hiberlibros.HiberLibros.interfaces.ILibroService;
import com.hiberlibros.HiberLibros.repositories.AutorLibroRepository;
import com.hiberlibros.HiberLibros.repositories.AutorRepository;
import java.util.ArrayList;

@Service
public class AutorService implements IAutorService {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private AutorLibroRepository autorLibroRepository;
    @Autowired
    private ILibroService libroService;
    @Autowired
    private ModelMapper obj;


    @Override
    public List<Autor> buscarAutores(String buscar) {
        return autorRepository.findByNombreContainsIgnoreCaseOrApellidosContainsIgnoreCase(buscar, buscar);
    }

    @Override
    public List<LibroDto> getLibros(Integer id) {
        return autorLibroRepository.findAll()
                .stream()
                .filter(z -> z.getAutor().getIdAutor() == id)
                .map(x -> obj.map(x.getLibro(), LibroDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void guardarAutor(Autor a) {
        a.setDesactivado(Boolean.FALSE);
        autorRepository.save(a);
    }

    @Override
    public Optional<Autor> encontrarAutor(Integer id) {
        return autorRepository.findByIdAutorAndDesactivado(id, Boolean.FALSE);
    }

    @Override
    public Boolean borrarAutor(Integer id) {
        Optional<Autor> a = encontrarAutor(id);
        List<Libro> l = new ArrayList<>();
        if (a.isPresent()) {
            l = libroService.encontrarPorAutor(a.get());
            if (l.isEmpty()) {//si no tiene libros se borra directamente
                autorRepository.deleteById(id);
                return true;
            } else {
                Boolean result = libroService.bajaLibrosList(l);
                if (!result) {//si es verdadero significa que no hay libros en intercambio por lo que se pueden desactivar
                    a.get().setDesactivado(Boolean.TRUE);
                    autorRepository.save(a.get());
                }
                return result;//devuelve true=si ha podido realizar la acción (no había libros en intercambio) false si no ha podido=libros en intercambio
            }
        } else {//si no existe el autor devuelve falso directamente
            return false;
        }
    }

    @Override
    public List<Autor> consultarAutores() {
        return autorRepository.findByDesactivado(Boolean.FALSE);
    }
}
