package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.entities.Genero;
import com.hiberlibros.HiberLibros.entities.Preferencia;
import com.hiberlibros.HiberLibros.entities.Usuario;
import com.hiberlibros.HiberLibros.interfaces.IGeneroService;
import com.hiberlibros.HiberLibros.interfaces.IPreferenciaService;
import com.hiberlibros.HiberLibros.interfaces.ISeguridadService;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Isabel
 */
@RestController
@RequestMapping("/preferenciaback")
public class PreferenciaController {

    @Autowired
    private IPreferenciaService prefService;
   
    @Autowired
    private IGeneroService serviceGenero;
    
    @Autowired
    private IUsuarioService usuServ;

    @Autowired
    private ISeguridadService serviceSeguridad;

    
    @GetMapping
    public Map<String, Object> verPreferencias(String mail) {
        
        Map<String, Object> m = new HashMap<>();
        Usuario u = usuServ.usuarioRegistrado(mail);
        m.put("preferencias", prefService.findByUsuario(u));
        m.put("generos", serviceGenero.getGeneros());
        m.put("formulario", new Preferencia());

        return m;
    }
    
     @PostMapping("/guardar")
    public void anadirPreferencia(Integer idGenero, String email) {
    
        
        Usuario u = usuServ.usuarioRegistrado(email);
        Genero gen = serviceGenero.encontrarPorId(idGenero);
        Preferencia pref = new Preferencia();
        pref.setGenero(gen);
        pref.setUsuario(u);
        
        prefService.addPreferencia(pref);
    }


    @GetMapping("/borrar")
    public void borrarPreferencia(Integer id) {
        
        prefService.borrarPreferencia(id);
    }  
}
