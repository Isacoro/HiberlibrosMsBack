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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("forosback")
public class ForoLibroController {

    @Autowired
    private IForoLibroService serviceForoLibro;

    @Autowired 
    private ISeguridadService serviceSeguridad;
    
    @Autowired
    private IUsuarioService usuService;
    
    @Autowired
    private ILibroService serviceLibro;
    
    @GetMapping("/libro")
    public Map<String, Object> recuperarForosPorLibro(Integer id) {
        Map<String, Object> m = new HashMap<>();
        m.put("foros",serviceForoLibro.recuperarForosDeLibro(serviceLibro.libroId(id)));
        m.put("libros",serviceLibro.libroId(id));
        return m;
    }
    
    @GetMapping()
    public Map<String,Object> recuperarForos() {
        Map<String,Object> m = new HashMap<>();
        m.put("foro", new ForoLibro());
        m.put("libros", serviceLibro.encontrarDisponible());
        m.put("foros", serviceForoLibro.recuperarTodosLosForos());
        return m;
    }
    
    @GetMapping("/alta")
    //public Map<String,Object> altaForo (ForoLibro l, String email){
     public Map<String,Object> altaForo(String tituloForo,Integer idLibro ,String email){
        ForoLibro l = new ForoLibro();
        l.setIdLibro(serviceLibro.libroId(idLibro));
        l.setTituloForo(tituloForo);
        l.setDesactivado(Boolean.FALSE);
        l.setUsuarioCreador(usuService.usuarioRegistrado(email));
        serviceForoLibro.altaForoLibro(l);
        
        Map<String,Object> m = new HashMap<>();
        m.put("foro", new ForoLibro());
        m.put("libros", serviceLibro.encontrarDisponible());
        m.put("foros", serviceForoLibro.recuperarTodosLosForos());

        return m;
    }
    
    
    @GetMapping("/baja")
    public void bajaForo (Integer id){
        serviceForoLibro.bajaForoLibro(id);
    }
    
}
