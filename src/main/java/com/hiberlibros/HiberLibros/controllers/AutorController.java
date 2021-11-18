package com.hiberlibros.HiberLibros.controllers;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hiberlibros.HiberLibros.entities.Autor;
import com.hiberlibros.HiberLibros.interfaces.IAutorService;
import com.hiberlibros.HiberLibros.interfaces.ILibroService;
import com.hiberlibros.HiberLibros.repositories.AutorLibroRepository;

@RestController
@RequestMapping("/autorback")
public class AutorController {

    @Autowired
    private AutorLibroRepository autorLibroRepository;
    @Autowired
    private ILibroService libroService;
    @Autowired
    private ModelMapper obj;
    @Autowired
    private IAutorService autorService;

    @GetMapping("/autores/listarAdmin")
    public Map<String, Object> listaAdmin(Model m, String borrado) {
        Map<String, Object> mo = new HashMap<>();
        
        mo.put("autores", autorService.consultarAutores());
        mo.put("autorForm", new Autor());
        if (borrado != null) {
            mo.put("borrado", borrado);
        }
        return mo;
    }
    
    @GetMapping("/librosAutor")
    public Map<String, Object> listaAdmin(Model m, Integer id) {
        Map<String, Object> mo = new HashMap<>();
        
    	Autor a = autorService.encontrarAutor(id).get();
        mo.put("libros", libroService.encontrarPorAutorActivos(a));
        return mo;
    }

    @GetMapping("/editarAutor")
    public Autor editarAutor(Model m, Integer id) {
        Autor autor = autorService.encontrarAutor(id).get();
        return autor;
    }

    @PostMapping("/guardarAutor")
    public void guardarAutor(Model m, Autor autor) {
        autorService.guardarAutor(autor);
    }

    @GetMapping("/eliminarAutor")
    public Boolean eliminarAutorAdmin( Integer id) {
        return autorService.borrarAutor(id);

    }
}
