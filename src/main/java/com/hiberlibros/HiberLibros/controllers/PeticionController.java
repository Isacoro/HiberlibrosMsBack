package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.entities.Peticion;
import com.hiberlibros.HiberLibros.entities.Usuario;
import com.hiberlibros.HiberLibros.interfaces.IPeticionService;
import com.hiberlibros.HiberLibros.interfaces.ISeguridadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("peticionback")
public class PeticionController {
    @Autowired
    private IPeticionService servicePeticion;
    @Autowired
    private IUsuarioService uService;
    @Autowired
    private ISeguridadService serviceSeguridad;
    
    @GetMapping(value = "/peticion")
    public Map<String,Object> peticion(Peticion p){
        
        Map<String,Object> m = new HashMap<>();
        if (p.getId()!=null){
            m.put("peticion", p);
        }
        m.put("peticiones",servicePeticion.consultaTodasPeticiones());
        return m;
    }
    
    @GetMapping(value = "/alta") //Recibe los integer y crea una nueva petición, vuelve al panel de usuario
    public void peticionAlta(Integer id_ul, String email){
        //Usuario u = uService.usuarioRegistrado(serviceSeguridad.getMailFromContext());
        Usuario u = uService.usuarioRegistrado(email);
        Peticion p=new Peticion();
        servicePeticion.insertaPeticion(p, id_ul, u); 
        
    }
    
    @PostMapping(value = "/baja")
    public void peticionBaja(Peticion p){
        servicePeticion.eliminaPeticion(p);
    }
    
    @GetMapping("/baja") //retira una solicitud solo con el ID de la petición para no tener que mandar un objeto petición
    public void retirarSolicitud(Integer id){
        servicePeticion.eliminarId(id);
    }
    
    @PostMapping(value = "/modificacion")
    public void peticionModificacion(Peticion p){
        servicePeticion.insertaModificaPeticion(p);
    }
    
    @PostMapping(value = "/aceptar")
    public void aceptarPeticion(Peticion p, Usuario u){
        servicePeticion.aceptarPeticion(p);
    }
            
    @PostMapping(value = "/rechazar")
    public void rechazarPeticion(Integer id, Usuario u){
        servicePeticion.rechazarPeticion(id);
    }
    
    @PostMapping(value = "/peticionesPendientes")
    public Map<String,Object> consultarTodasPeticionesPendientes(Usuario u){
         Map<String,Object> m = new HashMap<>();
         m.put("peticionesPendientes",servicePeticion.consultarPeticionesPendientes(u));
         return m;
    }
    
    public String consultarTodasPeticionesAceptadas(Model m,  Usuario u){
         m.addAttribute("peticionesAceptadas",servicePeticion.consultarPeticionesAceptadas(u));
         return "redirect:/peticion/peticion";
    }
    public String consultarTodasPeticionesRechazadas(Model m, Usuario u){
         m.addAttribute("peticionesRechazadas",servicePeticion.consultarPeticionesPendientes(u));
         return "redirect:/peticion/peticion";
    }
}
