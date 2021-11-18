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
import com.hiberlibros.HiberLibros.entities.UsuarioLibro;
import com.hiberlibros.HiberLibros.interfaces.IAutorService;
import com.hiberlibros.HiberLibros.interfaces.ILibroService;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioLibroService;
import com.hiberlibros.HiberLibros.repositories.AutorLibroRepository;
import com.hiberlibros.HiberLibros.repositories.AutorRepository;
import java.util.ArrayList;

@Service
public class AutorService implements IAutorService {

    @Autowired
    private AutorRepository autorRepo;

    @Autowired
    private AutorLibroRepository repo;

    @Autowired
    private ILibroService serviceLib;

    @Autowired
    private ModelMapper obj;


    @Override
    public List<Autor> buscarAutores(String buscar) {
        return autorRepo.findByNombreContainsIgnoreCaseOrApellidosContainsIgnoreCase(buscar, buscar);
//        return autorRepo.findAll().stream()
//                .filter(
//                        x -> x.getNombre().concat(x.getApellidos()).toLowerCase()//paso nombre completo a minusuclas para comparar
//                                .contains(buscar.toLowerCase())
//                )
//                .collect(Collectors.toList());
    }

    @Override
    public List<LibroDto> getLibros(Integer id) {
        return repo.findAll()
                .stream()
                .filter(z -> z.getAutor().getIdAutor() == id)
                .map(x -> obj.map(x.getLibro(), LibroDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void guardarAutor(Autor a) {
        a.setDesactivado(Boolean.FALSE);
        autorRepo.save(a);
    }

    @Override
    public Optional<Autor> encontrarAutor(Integer id) {
        return autorRepo.findByIdAutorAndDesactivado(id, Boolean.FALSE);
    }

    @Override
    public Boolean borrarAutor(Integer id) {
        Optional<Autor> a = encontrarAutor(id);
        List<Libro> l = new ArrayList<>();
        if (a.isPresent()) {
            l = serviceLib.encontrarPorAutor(a.get());
            if (l.isEmpty()) {//si no tiene libros se borra directamente
                autorRepo.deleteById(id);
                return true;
            } else {
                Boolean result = serviceLib.bajaLibrosList(l);
                if (!result) {//si es verdadero significa que no hay libros en intercambio por lo que se pueden desactivar
                    a.get().setDesactivado(Boolean.TRUE);
                    autorRepo.save(a.get());
                }
                return result;//devuelve true=si ha podido realizar la acción (no había libros en intercambio) false si no ha podido=libros en intercambio
            }
        } else {//si no existe el autor devuelve falso directamente
            return false;
        }
    }

    @Override
    public List<Autor> consultarAutores() {
        return autorRepo.findByDesactivado(Boolean.FALSE);
    }
}
