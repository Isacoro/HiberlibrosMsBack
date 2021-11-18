package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.entities.Genero;
import com.hiberlibros.HiberLibros.interfaces.IGeneroService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generoback")
public class GeneroController {

    @Autowired
    private IGeneroService serviceGen;

    //FUNCIONA OK
    @GetMapping
    public Map<String, Object> verGeneros() {
        Map<String, Object> m = new HashMap<>();
        m.put("generos", serviceGen.getGeneros());
        m.put("generoForm", new Genero());

        return m;
    }

    //FUNCIONA OK
    @PostMapping("/guardar")
    public void formulario(Genero genero) {
        serviceGen.guardarGenero(genero);
    }

    //FUNCIONA OK
    @GetMapping("/editar")
    public Genero editarGenero(Integer id) {
        Genero editGenero = serviceGen.encontrarPorId(id);
        return editGenero;
    }

    //FUNCIONA OK
    @GetMapping("/borrar")
    public Boolean borrarGenero(Integer id) {
      return serviceGen.borrarGenero(id);
    }

    //FUNCIONA OK
    @GetMapping("/listarAdmin")
    public Map<String, Object> listarTodo(Model m, String borrado) {
        Map<String, Object> mo = new HashMap<>();
        
        mo.put("generos", serviceGen.getGeneros());
        mo.put("generoForm", new Genero());
        if (borrado != null) {
            mo.put("borrado", borrado);
        }
        return mo;
    }
}
