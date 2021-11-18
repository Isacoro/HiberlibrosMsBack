package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.entities.Libro;
import com.hiberlibros.HiberLibros.interfaces.IAutorService;
import com.hiberlibros.HiberLibros.interfaces.IEditorialService;
import com.hiberlibros.HiberLibros.interfaces.IGeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hiberlibros.HiberLibros.interfaces.ILibroService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/librosback")
public class LibroController {

    @Autowired
    private ILibroService libroService;
    @Autowired
    private IGeneroService generoService;
    @Autowired
    private IEditorialService editorialService;
    @Autowired
    private IAutorService autorService;

    @GetMapping("/libros")
    public Map<String,Object> mostrarFormulario() {
        Map<String,Object> m = new HashMap<>();
        m.put("libros", libroService.encontrarDisponible());
        m.put("generos", generoService.getGeneros());
        m.put("editoriales", editorialService.consultaTodas());
        m.put("autores", autorService.consultarAutores());
        return m;
    }

    @PostMapping("/guardar")
    public void guardarLibro( Libro libro, Integer id_genero, Integer id_editorial, Integer id_autor) {
        libro.setGenero(generoService.encontrarPorId(id_genero));
        libro.setEditorial(editorialService.encontrarPorId(id_editorial));
        libro.setAutor(autorService.encontrarAutor(id_autor).get());
        libroService.guardarLibro(libro);
    }

    @GetMapping("/eliminar")
    public boolean eliminarLibro(Integer id) {
      
       return libroService.bajaLibroId(id);
    }

    @GetMapping("/modificar")
    public Map<String,Object> modificarLibro(Integer id) {
        Map<String,Object> mapa=new HashMap<>();
        mapa.put("imagen", libroService.libroId(id).getUriPortada());
        mapa.put("libro", libroService.libroId(id));
        mapa.put("generos", generoService.getGeneros());
        mapa.put("editoriales", editorialService.consultaTodas());
        mapa.put("autores", autorService.consultarAutores());

        return mapa;
    }

    @GetMapping("/listarAdmin")
    private  Map<String,Object>  listarTodo(String borrado) {
        Map<String,Object> mapa=new HashMap<>();
        mapa.put("libros", libroService.encontrarDisponible());
        mapa.put("generos", generoService.getGeneros());
        mapa.put("editoriales", editorialService.consultaTodas());
        mapa.put("autores", autorService.consultarAutores());
    
        return mapa;
    }

    @PostMapping("/guardarAdmin")
    public void guardarAdmin(Libro libro, Integer id_genero, Integer id_editorial, Integer id_autor) {
        libro.setGenero(generoService.encontrarPorId(id_genero));
        libro.setEditorial(editorialService.encontrarPorId(id_editorial));
        libro.setAutor(autorService.encontrarAutor(id_autor).get());
        libroService.guardarLibro(libro);

    }

    @GetMapping("/eliminarAdmin")
    public boolean eliminarAdmin(Integer id) {
        String borrado="";
        if (libroService.bajaLibroId(id)) {
            borrado="Borrado con éxito";
            return true;
        } else {
           borrado= "Error, no es posible borrar este autor";
           return false;
        }
    }

    @PostMapping("/addValoracionLibro")
    public void addValoracionLibro(Integer id, Integer valoracion) {

        libroService.valorarLibro(libroService.libroId(id), valoracion);
    }
}
