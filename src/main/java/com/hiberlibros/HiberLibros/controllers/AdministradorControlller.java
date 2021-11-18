package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.dtos.EventoDto;
import com.hiberlibros.HiberLibros.entities.Evento;
import com.hiberlibros.HiberLibros.interfaces.ILibroService;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioService;
import com.hiberlibros.HiberLibros.repositories.EventoRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/hiberlibros/paneladminBack")
public class AdministradorControlller {


    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private ILibroService libroService;
    @Autowired
    private EventoRepository eventoRepository;

    @GetMapping
    public Map<String,Object> adminHub(Model m) {
         Map<String,Object> mapa = new HashMap<>();
           mapa.put("numUsuarios", usuarioService.contarUsuarios());
           mapa.put("numLibros", libroService.contarLibros());
           mapa.put("eventos", eventoRepository.findAll());
        return  mapa;
    } 
    
    @GetMapping("/addEvent")
    public String formEvento(){
        return "administrador/eventoForm";
    }
   
    @PostMapping("/evento")
    public void addEvento(Evento e){  
    
        eventoRepository.save(e);
    }

     @GetMapping("/deleteEvento")
    @ResponseBody
      public void eliminar(Integer id){
          eventoRepository.deleteById(id);
      }

    @GetMapping("/buscarEvento")
    @ResponseBody
    public List<EventoDto> buscar(String search){
       return eventoRepository.findBySummaryContainingIgnoreCase(search).stream().map(x->new EventoDto(x.getId(),x.getSummary()) ).collect(Collectors.toList());
    }

    @GetMapping("/contacto")
    public String adminContacto() {
        return "administrador/contacto"; 
    }
}
