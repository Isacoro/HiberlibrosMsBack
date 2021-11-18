package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.entities.ComentarioForo;
import com.hiberlibros.HiberLibros.interfaces.IComentarioForoService;
import com.hiberlibros.HiberLibros.interfaces.IForoLibroService;
import com.hiberlibros.HiberLibros.interfaces.ISeguridadService;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioService;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("hilosback")
public class ComentarioForoController {

    @Autowired
    private IComentarioForoService comentarioForoService;
    @Autowired
    private IForoLibroService foroLibroService;
    @Autowired
    private IUsuarioService usuarioService;
    @Autowired 
    private ISeguridadService seguridadService;

    
    @GetMapping("/consultarPorForo")
    public Map<String, Object> consultarComentariosPorForo( Integer idForo){

        Map<String, Object> m = new HashMap<>();

        m.put("foro", foroLibroService.consultarForo(idForo));
        m.put("comentarios", comentarioForoService.consultarComentariosPorForo(foroLibroService.consultarForo(idForo)));
        
        return m;
    }
    
    @PostMapping("/alta")
    public Map<String,Object> altaComentario(Integer idForoLibro, String comentario,String email){

        ComentarioForo comentarioForo =  new ComentarioForo();
        comentarioForo.setComentarioForo(comentario);
        comentarioForo.setUsuarioComentario(usuarioService.usuarioRegistrado(email));
        comentarioForo.setForoLibro(foroLibroService.consultarForo(idForoLibro));
        comentarioForoService.altaComentario(comentarioForo);
        
        
        Map<String, Object> m = new HashMap<>();
        m.put("foro", foroLibroService.consultarForo(idForoLibro));
        m.put("comentarios", comentarioForoService.consultarComentariosPorForo(foroLibroService.consultarForo(idForoLibro)));
        
        return m;
    }
}
