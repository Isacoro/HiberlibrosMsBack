package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.dtos.EventoDTO;
import com.hiberlibros.HiberLibros.entities.Evento;
import com.hiberlibros.HiberLibros.interfaces.ILibroService;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioService;
import com.hiberlibros.HiberLibros.repositories.EventoRepository;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Mohamad
 */
@RestController
@RequestMapping("/hiberlibros/paneladminBack")
public class AdministradorControlller {


    @Autowired
    private IUsuarioService usuService;
    @Autowired
    private ILibroService libserv;
    @Autowired
    private EventoRepository evrepo;

    @GetMapping
    public Map<String,Object> adminHub(Model m) {
         Map<String,Object> mapa = new HashMap<>();
           mapa.put("numUsuarios",usuService.contarUsuarios());
           mapa.put("numLibros",libserv.contarLibros());
           mapa.put("eventos", evrepo.findAll());
        return  mapa;
    } 
    
    @GetMapping("/addEvent")
    public String formEvento(){
        return "administrador/eventoForm";
    }
   
    @PostMapping("/evento")
    public void addEvento(Evento e){  
    
        evrepo.save(e);
       
    }
     @GetMapping("/deleteEvento")
    @ResponseBody
      public void eliminar(Integer id){
          evrepo.deleteById(id);
      }
    @GetMapping("/buscarEvento")
    @ResponseBody
    public List<EventoDTO> buscar(String search){
       return evrepo.findBySummaryContainingIgnoreCase(search).stream().map(x->new EventoDTO(x.getId(),x.getSummary()) ).collect(Collectors.toList());
    }
    

    @GetMapping("/contacto")
    public String adminContacto() {
        return "administrador/contacto"; 
    }
}
