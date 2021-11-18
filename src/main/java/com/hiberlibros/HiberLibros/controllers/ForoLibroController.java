package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.entities.ForoLibro;
import com.hiberlibros.HiberLibros.interfaces.IForoLibroService;
import com.hiberlibros.HiberLibros.interfaces.ILibroService;
import com.hiberlibros.HiberLibros.interfaces.ISeguridadService;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("forosback")
public class ForoLibroController {

    @Autowired
    private IForoLibroService foroLibroService;
    @Autowired 
    private ISeguridadService seguridadService;
    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private ILibroService libroService;
    
    @GetMapping("/libro")
    public Map<String, Object> recuperarForosPorLibro(Integer id) {
        Map<String, Object> m = new HashMap<>();
        m.put("foros", foroLibroService.recuperarForosDeLibro(libroService.libroId(id)));
        m.put("libros", libroService.libroId(id));
        return m;
    }
    
    @GetMapping()
    public Map<String,Object> recuperarForos() {
        Map<String,Object> m = new HashMap<>();
        m.put("foro", new ForoLibro());
        m.put("libros", libroService.encontrarDisponible());
        m.put("foros", foroLibroService.recuperarTodosLosForos());
        return m;
    }
    
    @GetMapping("/alta")
     public Map<String,Object> altaForo(String tituloForo,Integer idLibro ,String email){
        ForoLibro l = new ForoLibro();
        l.setIdLibro(libroService.libroId(idLibro));
        l.setTituloForo(tituloForo);
        l.setDesactivado(Boolean.FALSE);
        l.setUsuarioCreador(usuarioService.usuarioRegistrado(email));
        foroLibroService.altaForoLibro(l);
        
        Map<String,Object> m = new HashMap<>();
        m.put("foro", new ForoLibro());
        m.put("libros", libroService.encontrarDisponible());
        m.put("foros", foroLibroService.recuperarTodosLosForos());

        return m;
    }

    @GetMapping("/baja")
    public void bajaForo (Integer id){
        foroLibroService.bajaForoLibro(id);
    }
}
