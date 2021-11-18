package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.dtos.RelatoDto;
import com.hiberlibros.HiberLibros.dtos.RelatoDtoAdmin;
import com.hiberlibros.HiberLibros.dtos.RelatoEnvioDto;
import com.hiberlibros.HiberLibros.dtos.TablaRelatoDto;
import com.hiberlibros.HiberLibros.entities.Relato;
import com.hiberlibros.HiberLibros.entities.Usuario;
import com.hiberlibros.HiberLibros.interfaces.IGeneroService;

import com.hiberlibros.HiberLibros.interfaces.IRelatoService;
import com.hiberlibros.HiberLibros.interfaces.ISeguridadService;
import com.hiberlibros.HiberLibros.repositories.RelatoRepository;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;
import org.springframework.web.bind.annotation.PathVariable;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioService;
import com.hiberlibros.HiberLibros.repositories.GeneroRepository;
import com.hiberlibros.HiberLibros.repositories.UsuarioRepository;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relatoback")
public class RelatoController {

    @Autowired
    private UsuarioRepository repoUsuario;

    @Autowired
    private GeneroRepository repoGenero;
    @Autowired
    private RelatoRepository repoRelato;
    @Autowired
    private IGeneroService serviceGenero;
    @Autowired
    private IUsuarioService usuService;
    @Autowired
    private ISeguridadService serviceSeguridad;
    @Autowired
    private IRelatoService relatoService;

    private final String RUTA_BASE = "c:\\zzzzSubirFicheros\\";

    @GetMapping
    public String prueba(Model model) {

        model.addAttribute("generos", serviceGenero.getGeneros());
        model.addAttribute("relatos", repoRelato.findAll());
        return "/principal/relato";
    }

    @GetMapping("/listaRelatos")
    public Map<String, Object> mostrarRelatos(String mail) {
        Usuario u = usuService.usuarioRegistrado(mail);
        Map<String, Object> m = new HashMap<>();
        m.put("relatos", repoRelato.findAll());
        m.put("usuario", u);
        return m;

    }

    @GetMapping("/eliminarRelato")
    public void eliminarRelato(Integer id) {
        Optional<Relato> rel = repoRelato.findById(id);
        if (rel.isPresent()) {
            repoRelato.deleteById(id);
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
            Optional<Relato> rel = repoRelato.findById(id);
            if (rel.isPresent()) {
                calcularValoracion(id, valoracion);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //metodo para calcular el numero de valoraciones y calcular la media entre ellas

    public void calcularValoracion(int id, Double valoracion) {
        Optional<Relato> relato = repoRelato.findById(id);
        if (relato.isPresent()) {
            relato.get().setNumeroValoraciones(relato.get().getNumeroValoraciones() + 1);
            Double val = (relato.get().getValoracionUsuarios() * (relato.get().getNumeroValoraciones() - 1) + valoracion)
                    / relato.get().getNumeroValoraciones();
            double redondeo = Math.round(val * 100.0) / 100.0;
            relato.get().setValoracionUsuarios(redondeo);
            repoRelato.save(relato.get());

        }
    }

    @GetMapping("/modificar")
    public Map<String, Object> modificarRelato(Integer id) {
        Map<String, Object> m = new HashMap<>();
        m.put("relato", repoRelato.findById(id));
        m.put("generos", serviceGenero.getGeneros());
        return m;
    }

    @PostMapping("/modificarRelato")
    public void modificarRelato(Integer id, Integer idGenero, String titulo) {
        Relato relato = repoRelato.findById(id).get();
        relato.setTitulo(titulo);
        relato.setGenero(repoGenero.findById(idGenero).get());
        repoRelato.save(relato);

    }

    @GetMapping("/listarAdmin")
    private List<Relato> listarTodo(Model m) {
        List<Relato> lis = repoRelato.findAll();
        return lis;
    }

    @GetMapping("/eliminarAdmin")
    public String eliminarRelatoAdmin(Model m, Integer id) {
        Optional<Relato> rel = repoRelato.findById(id);
        if (rel.isPresent()) {
            repoRelato.deleteById(id);
        }
        return "redirect:listarAdmin";
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> descargar(String descargar) throws IOException {

        File file = new File(descargar);
        Relato rel = repoRelato.findByFichero(descargar);
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
        m.addAttribute("usuario", usuService.usuarioId(id));
        if (busqueda == null) {
            m.addAttribute("relatos", repoRelato.findAll());
        } else {
            m.addAttribute("relatos", repoRelato.findByTituloContainingIgnoreCase(busqueda));
        }

        return "/principal/buscarRelatos";
    }

    @GetMapping("/buscarPorValoracionMayor")
    public String mostrarPorValoracionMayor(Model model, Integer id) {

        model.addAttribute("generos", serviceGenero.getGeneros());
        model.addAttribute("relatos", relatoService.buscarPorValoracionMenorAMayor());
        model.addAttribute("usuario", usuService.usuarioId(id));
        return "/principal/buscarRelatos";
    }

    @GetMapping("/buscarPorValoracionMenor")
    public String mostrarPorValoracionMenor(Model model, Integer id) {

        model.addAttribute("generos", serviceGenero.getGeneros());
        model.addAttribute("relatos", relatoService.buscarPorValoracionMayorAMenor());
        model.addAttribute("usuario", usuService.usuarioId(id));
        return "/principal/buscarRelatos";
    }

    @GetMapping("/tablaRelato")
    public List<TablaRelatoDto> tablaRelato() {

        return relatoService.todos().stream().map(x -> new TablaRelatoDto(x.getTitulo(), x.getValoracionUsuarios(), x.getGenero().getNombre(),
                x.getNumeroValoraciones(), x.getId())).collect(Collectors.toList());
    }

}
