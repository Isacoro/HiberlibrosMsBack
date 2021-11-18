/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.dtos.RelatoDto;
import com.hiberlibros.HiberLibros.dtos.RelatoEnvioDto;
import com.hiberlibros.HiberLibros.dtos.TablaLibrosDto;
import com.hiberlibros.HiberLibros.entities.Autor;
import com.hiberlibros.HiberLibros.entities.Genero;
import com.hiberlibros.HiberLibros.entities.Libro;
import com.hiberlibros.HiberLibros.entities.Peticion;
import com.hiberlibros.HiberLibros.entities.Relato;
import com.hiberlibros.HiberLibros.entities.Usuario;
import com.hiberlibros.HiberLibros.entities.UsuarioLibro;
import com.hiberlibros.HiberLibros.interfaces.IAutorService;
import com.hiberlibros.HiberLibros.interfaces.IEditorialService;
import com.hiberlibros.HiberLibros.interfaces.IGeneroService;
import com.hiberlibros.HiberLibros.interfaces.ISeguridadService;

import com.hiberlibros.HiberLibros.interfaces.IIntercambioService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.hiberlibros.HiberLibros.interfaces.ILibroService;
import com.hiberlibros.HiberLibros.interfaces.IPeticionService;
import com.hiberlibros.HiberLibros.interfaces.IRelatoService;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioLibroService;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("/hiberlibrosback")
public class InicioController {

    @Autowired
    private IUsuarioService usuService;
    @Autowired
    private IGeneroService serviceGen;
    @Autowired
    private IAutorService serviceAutor;
    @Autowired
    private IEditorialService editoService;
    @Autowired
    private ILibroService liService;
    @Autowired
    private IRelatoService serviceRelato;
    @Autowired
    private IUsuarioLibroService ulService;
    @Autowired
    private IPeticionService petiService;
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private IIntercambioService serviceInter;

    @Autowired
    private ISeguridadService serviceSeguridad;

    private final String RUTA_BASE = "c:\\zzzzSubirFicheros\\";

    @GetMapping
    public String inicio(Model m, String error) {
        if (error != null) {
            m.addAttribute("error", error);
        }

        return "/principal/login";
    }

    @GetMapping("/pruebaContexto")
    @ResponseBody
    public String pruebaContexto() {
        return serviceSeguridad.getMailFromContext();
    }

    @PostMapping("/loginentrar")
    public String inicio(Model m, String username, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth = manager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        List<String> roles = auth.getAuthorities().stream().map(x -> x.getAuthority()).collect(Collectors.toList());
        for (String rol : roles) {
            if ("ROLE_Administrador".equals(rol)) {
                return "redirect:/hiberlibros/paneladmin";
            } else {
                if ("ROLE_Usuario".equals(rol)) {
                    return "redirect:/hiberlibros/panelUsuario";
                }
            }
        }
        String error = "Usuario no registrado";
        return "redirect:/hiberlibros?error=" + error;
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "/principal/logout";
    }

    @GetMapping("/panelUsuario") //entrada al panel principal de usuario, se pasan todos los elementos que se han de mostrar
    public Map<String,Object> panelUsuario(String mail) {
        Map<String,Object> m=new HashMap<>();
        Usuario u = usuService.usuarioRegistrado(mail);
        List<UsuarioLibro> ul = ulService.buscarUsuario(u);
        m.put("relatos", serviceRelato.encontrarPorAutor(u)); 
        m.put("usuario", u);
        m.put("libros", ulService.buscarUsuariotiene(u));
        m.put("misPeticiones", petiService.consutarPeticionesUsuarioPendientes(u));
        m.put("petiRecibidas", petiService.consultarPeticonesRecibidas(u));
        m.put("intercambiosPropios", serviceInter.encontrarULPrestador(ul));
        m.put("intercambiosPeticiones", serviceInter.encontrarULPrestatario(ul));
        m.put("librosUsuario", ulService.contarLibrosPorUsuario(u));
        m.put("numIntercambioPendiente", serviceInter.contarIntercambiosPendientes(ul));

        return m;
    }

    @GetMapping("/guardarLibro") //Guarda libros en la base de datos. Primero guarda un libro, y posteriormente lo mete en la tabla Usuario Libros
    public  Map<String,Object> formularioLibro(String buscador) {
        Map<String,Object> m=new HashMap<>();
        List<Libro> libros = new ArrayList<>();
        String noLibros = "";       
        m.put("autores", serviceAutor.consultarAutores());//autores para el desplegable
        m.put("generos", serviceGen.getGeneros());//géneros formulario
        m.put("editoriales", editoService.consultaTodas());//editoriales formulario
        if (buscador != null) {//si es distinto de nulo buscara el libro por isbn o título en la base de datos
            libros = liService.buscarLibro(buscador);
            m.put("libros", libros);
            if (libros.isEmpty()) {
                noLibros = "Ningun libro encontrado"; //si no existe devuelve un mensaje de error
            } else {
                noLibros = "encontrado";
            }
        }
        m.put("noLibros", noLibros);//devuelve el mensaje de error o de éxito
        return m;
    }

    @PostMapping("/guardarLibro") //guarda un libro en el UsuarioLibro si ese libro existe previamente en la base de datos
    public void guardarLibro(Integer libro, UsuarioLibro ul, String email) {
        Usuario u = usuService.usuarioRegistrado(email);
        Libro l = liService.libroId(libro);
        if (l.getValoracionLibro() == null) {
            l.setValoracionLibro(new Double(0));
            l.setNumeroValoraciones(0);
        } else {
            l.setNumeroValoraciones(1);
        }

        ulService.guardar(ul, l, u);
    }

    @PostMapping("/saveAutor")//Guarda un autor y vuelve a la página de registrar libro
    public void insertarAutor(Autor autor) {
        serviceAutor.guardarAutor(autor);
    }

    @PostMapping("/registroLibro")//Guarda un libro nuevo y luego lo guarda en Usuario Libro
    public void registrarLibro(String quieroTengo, String estadoConservacion, Libro l, Integer id_genero, Integer id_editorial, Integer id_autor, String email) {
        l.setGenero(serviceGen.encontrarPorId(id_genero));
        l.setEditorial(editoService.consultaPorIdEditorial(id_editorial));
        l.setAutor(serviceAutor.encontrarAutor(id_autor).get());
        l.setNumeroValoraciones(1);
        liService.guardarLibro(l);
        UsuarioLibro ul=new UsuarioLibro();        
        ul.setQuieroTengo(quieroTengo);
        ul.setEstadoConservacion(estadoConservacion);
        ul.setEstadoPrestamo("Libre");
        Usuario u = usuService.usuarioRegistrado(email);
        ulService.guardar(ul, l, u);
    }

    @PostMapping("/guardarRelato")
    public void formularioRelato(RelatoEnvioDto relatoDto, MultipartFile ficherosubido) {
        Relato relato=new Relato();
        relato.setTitulo(relatoDto.getTitulo());
        Genero genero=serviceGen.encontrarPorId(relatoDto.getIdGenero());
        Usuario u=usuService.usuarioRegistrado(relatoDto.getEmail());
        relato.setGenero(genero);
        relato.setUsuario(u);
        serviceRelato.guardarRelato(RUTA_BASE, relato, ficherosubido, relatoDto.getIdUsuarioRelato());
    }

    @GetMapping("/relato")
    public Map<String, Object> insertarRelato(Integer id) {
        Map<String, Object> m = new HashMap<>();
        m.put("generos", serviceGen.getGeneros());
        m.put("relatos", serviceRelato.todos());
        m.put("usuario", usuService.usuarioId(id));
        return m;
    }

    @GetMapping("/borrarUL")//borra un libro de UsuarioLibro sin eliminarlo de la tabla de Libros
    public String borrarUsuLibro(Integer id) {
        String borrado="";
        if (ulService.borrar(id)) {
            borrado="Borrado con éxito";
        } else {
             borrado= "Error, no es posible borrar este libro";
        }
        return borrado;
    }

    @GetMapping("/gestionarPeticion")
    public Map<String, Object> gestionarPeticion(Integer id) {
        Map<String, Object> m = new HashMap<>();
        Peticion p = petiService.consultarPeticionId(id);
        m.put("peticiones", p);
        m.put("librosSolicitante", ulService.buscarUsuarioDisponibilidad(p.getIdUsuarioSolicitante(), "Tengo", "Libre"));
        return m;
    }

    @PostMapping("/realizarIntercambio")
    public void realizarIntercambio(Integer id_peticion, Integer usuarioPrestatario) {
        Peticion p = petiService.consultarPeticionId(id_peticion);
        UsuarioLibro ulPrestatario = ulService.encontrarId(usuarioPrestatario);
        UsuarioLibro ulPrestador = p.getIdUsuarioLibro();
        serviceInter.guardarIntercambio(ulPrestatario, ulPrestador);
        petiService.aceptarPeticion(p);
    }

    @GetMapping("/rechazarIntercambio")
    public void rechazarIntercambio(Integer id) {
        petiService.rechazarPeticion(id);
    }

    @GetMapping("/finIntercambio")
    public void finIntercambio(Integer id) {
        serviceInter.finIntercambio(id);
    }

    @GetMapping("/editarUsuario")
    public Usuario editar(String email) {
        return usuService.usuarioRegistrado(email);
    }

    @GetMapping("/tablaBuscarLibro")
    public List<TablaLibrosDto> tablaBuscarLibro(String email) {
        Usuario u = usuService.usuarioRegistrado(email);
        List<UsuarioLibro> ul = ulService.buscarDisponibles(u);
        List<TablaLibrosDto> tld = ul.stream().map(x -> new TablaLibrosDto(
                x.getId(),
                x.getLibro().getId(),
                x.getLibro().getUriPortada(),
                x.getLibro().getIsbn(),
                x.getLibro().getTitulo(),
                x.getLibro().getAutor().getNombre() + " " + x.getLibro().getAutor().getApellidos(),
                x.getLibro().getIdioma(),
                x.getLibro().getEditorial().getNombreEditorial(),
                x.getLibro().getValoracionLibro(),
                x.getEstadoConservacion(),
                x.getUsuario().getNombre()))
                .collect(Collectors.toList());

        return tld;
    }

}
