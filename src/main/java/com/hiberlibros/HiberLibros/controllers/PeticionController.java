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

@RestController
@RequestMapping("peticionback")
public class PeticionController {

    @Autowired
    private IPeticionService peticionService;
    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private ISeguridadService seguridadService;
    
    @GetMapping(value = "/peticion")
    public Map<String,Object> peticion(Peticion p){
        
        Map<String,Object> m = new HashMap<>();
        if (p.getId()!=null){
            m.put("peticion", p);
        }
        m.put("peticiones", peticionService.consultaTodasPeticiones());
        return m;
    }

    //Recibe los integer y crea una nueva petición, vuelve al panel de usuario
    @GetMapping(value = "/alta")
    public void peticionAlta(Integer id_ul, String email){

        Usuario u = usuarioService.usuarioRegistrado(email);
        Peticion p=new Peticion();
        peticionService.insertaPeticion(p, id_ul, u);
        
    }
    
    @PostMapping(value = "/baja")
    public void peticionBaja(Peticion p){
        peticionService.eliminaPeticion(p);
    }

    //Retira una solicitud solo con el ID de la petición para no tener que mandar un objeto petición
    @GetMapping("/baja")
    public void retirarSolicitud(Integer id){
        peticionService.eliminarId(id);
    }
    
    @PostMapping(value = "/modificacion")
    public void peticionModificacion(Peticion p){
        peticionService.insertaModificaPeticion(p);
    }
    
    @PostMapping(value = "/aceptar")
    public void aceptarPeticion(Peticion p, Usuario u){
        peticionService.aceptarPeticion(p);
    }
            
    @PostMapping(value = "/rechazar")
    public void rechazarPeticion(Integer id, Usuario u){
        peticionService.rechazarPeticion(id);
    }
    
    @PostMapping(value = "/peticionesPendientes")
    public Map<String,Object> consultarTodasPeticionesPendientes(Usuario u){
         Map<String,Object> m = new HashMap<>();
         m.put("peticionesPendientes", peticionService.consultarPeticionesPendientes(u));
         return m;
    }
    
    public String consultarTodasPeticionesAceptadas(Model m,  Usuario u){
         m.addAttribute("peticionesAceptadas", peticionService.consultarPeticionesAceptadas(u));
         return "redirect:/peticion/peticion";
    }
    public String consultarTodasPeticionesRechazadas(Model m, Usuario u){
         m.addAttribute("peticionesRechazadas", peticionService.consultarPeticionesPendientes(u));
         return "redirect:/peticion/peticion";
    }
}
