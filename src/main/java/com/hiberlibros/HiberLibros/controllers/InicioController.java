package com.hiberlibros.HiberLibros.controllers;

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


@RestController
@RequestMapping("/hiberlibrosback")
public class InicioController {

    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private IGeneroService generoService;
    @Autowired
    private IAutorService autorService;
    @Autowired
    private IEditorialService editorialService;
    @Autowired
    private ILibroService libroService;
    @Autowired
    private IRelatoService relatoService;
    @Autowired
    private IUsuarioLibroService usuarioLibroService;
    @Autowired
    private IPeticionService peticionService;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private IIntercambioService intercambioService;
    @Autowired
    private ISeguridadService seguridadService;

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
        return seguridadService.getMailFromContext();
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

    //Entrada al panel principal de usuario, se pasan todos los elementos que se han de mostrar
    @GetMapping("/panelUsuario")
    public Map<String,Object> panelUsuario(String mail) {
        Map<String,Object> m=new HashMap<>();
        Usuario u = usuarioService.usuarioRegistrado(mail);
        List<UsuarioLibro> ul = usuarioLibroService.buscarUsuario(u);
        m.put("relatos", relatoService.encontrarPorAutor(u));
        m.put("usuario", u);
        m.put("libros", usuarioLibroService.buscarUsuariotiene(u));
        m.put("misPeticiones", peticionService.consutarPeticionesUsuarioPendientes(u));
        m.put("petiRecibidas", peticionService.consultarPeticonesRecibidas(u));
        m.put("intercambiosPropios", intercambioService.encontrarULPrestador(ul));
        m.put("intercambiosPeticiones", intercambioService.encontrarULPrestatario(ul));
        m.put("librosUsuario", usuarioLibroService.contarLibrosPorUsuario(u));
        m.put("numIntercambioPendiente", intercambioService.contarIntercambiosPendientes(ul));

        return m;
    }

    //Guarda libros en la base de datos. Primero guarda un libro, y posteriormente lo mete en la tabla Usuario Libros
    @GetMapping("/guardarLibro")
    public  Map<String,Object> formularioLibro(String buscador) {
        Map<String,Object> m=new HashMap<>();
        List<Libro> libros = new ArrayList<>();
        String noLibros = "";       
        m.put("autores", autorService.consultarAutores());//autores para el desplegable
        m.put("generos", generoService.getGeneros());//géneros formulario
        m.put("editoriales", editorialService.consultaTodas());//editoriales formulario
        if (buscador != null) {//si es distinto de nulo buscara el libro por isbn o título en la base de datos
            libros = libroService.buscarLibro(buscador);
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

    //Guarda un libro en el UsuarioLibro si ese libro existe previamente en la base de datos
    @PostMapping("/guardarLibro")
    public void guardarLibro(Integer libro, UsuarioLibro ul, String email) {
        Usuario u = usuarioService.usuarioRegistrado(email);
        Libro l = libroService.libroId(libro);
        if (l.getValoracionLibro() == null) {
            l.setValoracionLibro(new Double(0));
            l.setNumeroValoraciones(0);
        } else {
            l.setNumeroValoraciones(1);
        }

        usuarioLibroService.guardar(ul, l, u);
    }

    //Guarda un autor y vuelve a la página de registrar libro
    @PostMapping("/saveAutor")
    public void insertarAutor(Autor autor) {
        autorService.guardarAutor(autor);
    }

    //Guarda un libro nuevo y luego lo guarda en Usuario Libro
    @PostMapping("/registroLibro")
    public void registrarLibro(String quieroTengo, String estadoConservacion, Libro l, Integer id_genero, Integer id_editorial, Integer id_autor, String email) {
        l.setGenero(generoService.encontrarPorId(id_genero));
        l.setEditorial(editorialService.consultaPorIdEditorial(id_editorial));
        l.setAutor(autorService.encontrarAutor(id_autor).get());
        l.setNumeroValoraciones(1);
        libroService.guardarLibro(l);
        UsuarioLibro ul=new UsuarioLibro();        
        ul.setQuieroTengo(quieroTengo);
        ul.setEstadoConservacion(estadoConservacion);
        ul.setEstadoPrestamo("Libre");
        Usuario u = usuarioService.usuarioRegistrado(email);
        usuarioLibroService.guardar(ul, l, u);
    }

    @PostMapping("/guardarRelato")
    public void formularioRelato(RelatoEnvioDto relatoDto, MultipartFile ficherosubido) {
        Relato relato=new Relato();
        relato.setTitulo(relatoDto.getTitulo());
        Genero genero= generoService.encontrarPorId(relatoDto.getIdGenero());
        Usuario u= usuarioService.usuarioRegistrado(relatoDto.getEmail());
        relato.setGenero(genero);
        relato.setUsuario(u);
        relatoService.guardarRelato(RUTA_BASE, relato, ficherosubido, relatoDto.getIdUsuarioRelato());
    }

    @GetMapping("/relato")
    public Map<String, Object> insertarRelato(Integer id) {
        Map<String, Object> m = new HashMap<>();
        m.put("generos", generoService.getGeneros());
        m.put("relatos", relatoService.todos());
        m.put("usuario", usuarioService.usuarioId(id));
        return m;
    }

    //Borra un libro de UsuarioLibro sin eliminarlo de la tabla de Libros
    @GetMapping("/borrarUL")
    public String borrarUsuLibro(Integer id) {
        String borrado="";
        if (usuarioLibroService.borrar(id)) {
            borrado="Borrado con éxito";
        } else {
             borrado= "Error, no es posible borrar este libro";
        }
        return borrado;
    }

    @GetMapping("/gestionarPeticion")
    public Map<String, Object> gestionarPeticion(Integer id) {
        Map<String, Object> m = new HashMap<>();
        Peticion p = peticionService.consultarPeticionId(id);
        m.put("peticiones", p);
        m.put("librosSolicitante", usuarioLibroService.buscarUsuarioDisponibilidad(p.getIdUsuarioSolicitante(), "Tengo", "Libre"));
        return m;
    }

    @PostMapping("/realizarIntercambio")
    public void realizarIntercambio(Integer id_peticion, Integer usuarioPrestatario) {
        Peticion p = peticionService.consultarPeticionId(id_peticion);
        UsuarioLibro ulPrestatario = usuarioLibroService.encontrarId(usuarioPrestatario);
        UsuarioLibro ulPrestador = p.getIdUsuarioLibro();
        intercambioService.guardarIntercambio(ulPrestatario, ulPrestador);
        peticionService.aceptarPeticion(p);
    }

    @GetMapping("/rechazarIntercambio")
    public void rechazarIntercambio(Integer id) {
        peticionService.rechazarPeticion(id);
    }

    @GetMapping("/finIntercambio")
    public void finIntercambio(Integer id) {
        intercambioService.finIntercambio(id);
    }

    @GetMapping("/editarUsuario")
    public Usuario editar(String email) {
        return usuarioService.usuarioRegistrado(email);
    }

    @GetMapping("/tablaBuscarLibro")
    public List<TablaLibrosDto> tablaBuscarLibro(String email) {
        Usuario u = usuarioService.usuarioRegistrado(email);
        List<UsuarioLibro> ul = usuarioLibroService.buscarDisponibles(u);
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
