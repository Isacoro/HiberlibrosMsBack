/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.dtos.ComentarioForoDto;
import com.hiberlibros.HiberLibros.dtos.ForoLibroDto;
import com.hiberlibros.HiberLibros.entities.ComentarioForo;
import com.hiberlibros.HiberLibros.entities.ForoLibro;
import com.hiberlibros.HiberLibros.interfaces.IComentarioForoService;
import com.hiberlibros.HiberLibros.interfaces.IForoLibroService;
import com.hiberlibros.HiberLibros.interfaces.ISeguridadService;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
@RequestMapping("hilosback")
public class ComentarioForoController {
    @Autowired
    private IComentarioForoService serviceComentarioForo; 
    
    @Autowired
    private IForoLibroService serviceForoLibroService;
    
    @Autowired
    private IUsuarioService usuService;
    
    @Autowired 
    private ISeguridadService serviceSeguridad;
    

    
    @GetMapping("/consultarPorForo")
    public Map<String, Object> consultarComentariosPorForo( Integer idForo){

        Map<String, Object> m = new HashMap<>();

        m.put("foro", serviceForoLibroService.consultarForo(idForo));
        m.put("comentarios",serviceComentarioForo.consultarComentariosPorForo(serviceForoLibroService.consultarForo(idForo)));
        
        return m;
    }
    
    @PostMapping("/alta")
    public Map<String,Object> altaComentario(Integer idForoLibro, String comentario,String email){
        
        
        
        ComentarioForo comentarioForo =  new ComentarioForo();
        comentarioForo.setComentarioForo(comentario);
        comentarioForo.setUsuarioComentario(usuService.usuarioRegistrado(email));
        comentarioForo.setForoLibro(serviceForoLibroService.consultarForo(idForoLibro));
        serviceComentarioForo.altaComentario(comentarioForo);
        
        
        Map<String, Object> m = new HashMap<>();
        m.put("foro", serviceForoLibroService.consultarForo(idForoLibro));
        m.put("comentarios",serviceComentarioForo.consultarComentariosPorForo(serviceForoLibroService.consultarForo(idForoLibro)));
        
        return m;
    }
    
}
