package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.dtos.TablaRelatoDto;
import com.hiberlibros.HiberLibros.entities.Relato;
import com.hiberlibros.HiberLibros.entities.Usuario;
import com.hiberlibros.HiberLibros.interfaces.IGeneroService;

import com.hiberlibros.HiberLibros.interfaces.IRelatoService;
import com.hiberlibros.HiberLibros.interfaces.ISeguridadService;
import com.hiberlibros.HiberLibros.repositories.RelatoRepository;
import java.io.File;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import com.hiberlibros.HiberLibros.interfaces.IUsuarioService;
import com.hiberlibros.HiberLibros.repositories.GeneroRepository;
import com.hiberlibros.HiberLibros.repositories.UsuarioRepository;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relatoback")
public class RelatoController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private GeneroRepository generoRepository;
    @Autowired
    private RelatoRepository relatoRepository;
    @Autowired
    private IGeneroService generoService;
    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private ISeguridadService seguridadService;
    @Autowired
    private IRelatoService relatoService;

    private final String RUTA_BASE = "c:\\zzzzSubirFicheros\\";

    @GetMapping
    public String prueba(Model model) {

        model.addAttribute("generos", generoService.getGeneros());
        model.addAttribute("relatos", relatoRepository.findAll());
        return "/principal/relato";
    }

    @GetMapping("/listaRelatos")
    public Map<String, Object> mostrarRelatos(String mail) {
        Usuario u = usuarioService.usuarioRegistrado(mail);
        Map<String, Object> m = new HashMap<>();
        m.put("relatos", relatoRepository.findAll());
        m.put("usuario", u);
        return m;
    }

    @GetMapping("/eliminarRelato")
    public void eliminarRelato(Integer id) {
        Optional<Relato> rel = relatoRepository.findById(id);
        if (rel.isPresent()) {
            relatoRepository.deleteById(id);
        }
        String rutarchivo = rel.get().getFichero();
        try {
            Files.delete(Path.of(rutarchivo));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @PostMapping("/addValoracion")
    public void addValoracion(Double valoracion, Integer id, Integer idUsuario) {
        try {
            Optional<Relato> rel = relatoRepository.findById(id);
            if (rel.isPresent()) {
                calcularValoracion(id, valoracion);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Metodo para calcular el numero de valoraciones y calcular la media entre ellas
    public void calcularValoracion(int id, Double valoracion) {
        Optional<Relato> relato = relatoRepository.findById(id);
        if (relato.isPresent()) {
            relato.get().setNumeroValoraciones(relato.get().getNumeroValoraciones() + 1);
            Double val = (relato.get().getValoracionUsuarios() * (relato.get().getNumeroValoraciones() - 1) + valoracion)
                    / relato.get().getNumeroValoraciones();
            double redondeo = Math.round(val * 100.0) / 100.0;
            relato.get().setValoracionUsuarios(redondeo);
            relatoRepository.save(relato.get());
        }
    }

    @GetMapping("/modificar")
    public Map<String, Object> modificarRelato(Integer id) {
        Map<String, Object> m = new HashMap<>();
        m.put("relato", relatoRepository.findById(id));
        m.put("generos", generoService.getGeneros());
        return m;
    }

    @PostMapping("/modificarRelato")
    public void modificarRelato(Integer id, Integer idGenero, String titulo) {
        Relato relato = relatoRepository.findById(id).get();
        relato.setTitulo(titulo);
        relato.setGenero(generoRepository.findById(idGenero).get());
        relatoRepository.save(relato);
    }

    @GetMapping("/listarAdmin")
    private List<Relato> listarTodo(Model m) {
        List<Relato> lis = relatoRepository.findAll();
        return lis;
    }

    @GetMapping("/eliminarAdmin")
    public String eliminarRelatoAdmin(Model m, Integer id) {
        Optional<Relato> rel = relatoRepository.findById(id);
        if (rel.isPresent()) {
            relatoRepository.deleteById(id);
        }
        return "redirect:listarAdmin";
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> descargar(String descargar) throws IOException {

        File file = new File(descargar);
        Relato rel = relatoRepository.findByFichero(descargar);
        String titulo = rel.getTitulo();
        String extension = descargar.substring(descargar.lastIndexOf("."));

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Disposition", "attachment; filename=" + titulo + "." + extension);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(descargar));
            return ResponseEntity.ok()
                    .headers(header)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/buscarRelato")
    public String buscarRelato(Model m, Integer id, String busqueda) {
        m.addAttribute("usuario", usuarioService.usuarioId(id));
        if (busqueda == null) {
            m.addAttribute("relatos", relatoRepository.findAll());
        } else {
            m.addAttribute("relatos", relatoRepository.findByTituloContainingIgnoreCase(busqueda));
        }

        return "/principal/buscarRelatos";
    }

    @GetMapping("/buscarPorValoracionMayor")
    public String mostrarPorValoracionMayor(Model model, Integer id) {

        model.addAttribute("generos", generoService.getGeneros());
        model.addAttribute("relatos", relatoService.buscarPorValoracionMenorAMayor());
        model.addAttribute("usuario", usuarioService.usuarioId(id));
        return "/principal/buscarRelatos";
    }

    @GetMapping("/buscarPorValoracionMenor")
    public String mostrarPorValoracionMenor(Model model, Integer id) {

        model.addAttribute("generos", generoService.getGeneros());
        model.addAttribute("relatos", relatoService.buscarPorValoracionMayorAMenor());
        model.addAttribute("usuario", usuarioService.usuarioId(id));
        return "/principal/buscarRelatos";
    }

    @GetMapping("/tablaRelato")
    public List<TablaRelatoDto> tablaRelato() {

        return relatoService.todos().stream().map(x -> new TablaRelatoDto(x.getTitulo(), x.getValoracionUsuarios(), x.getGenero().getNombre(),
                x.getNumeroValoraciones(), x.getId())).collect(Collectors.toList());
    }
}
