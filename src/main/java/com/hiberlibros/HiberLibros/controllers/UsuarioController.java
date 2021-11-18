package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioService;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/usuariosback")
public class UsuarioController {

    @Autowired
    private IUsuarioService serviceUsuario;


    @Value("${carpetas.recursos.hiberlibros}")
    private String rutaBase;

    //Devuelve una lista con todos los usuarios, parte administrador
    @GetMapping
    public Map<String, Object> usuarioFormulario(String registro) {
        Map<String, Object> m=new HashMap<>();        
        m.put("registro", registro);
        m.put("usuarios", serviceUsuario.usuariosList());
        return m;
    }

    @PostMapping("/guardarUsuario")
    public String usuarioRegistrar(Usuario u, String password) { 
        System.out.println(u.getApellido()+"baaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaack");
       return serviceUsuario.guardarUsuarioYSeguridad(u, password);
    }

    //Edita usuario, manda el usuario para rellenar el formulario
    @PostMapping("/editarUsuario")
    public void usuarioEditar(Usuario u) {
        serviceUsuario.editarUsuario(u);
    }

    @GetMapping("/borrar")
    public Boolean borrar(Integer id) {//borra usuario por ID en administrador
        return serviceUsuario.borrarUsuario(id);    
    }

    //borra usuario por ID en web vista usuario
    @GetMapping("/borrarUsuario")
    public String borrarUsuario(Integer id) {
        serviceUsuario.borrarUsuario(id);
        return "redirect:/hiberlibros";
    }

    @GetMapping("/listarAdmin")
    public List<Usuario> listarTodo() {
        return serviceUsuario.usuariosList();
    }

    @PostMapping("/altaAdmin")
    public String altaAdmin(Usuario u, String password) {
        return serviceUsuario.guardarUsuarioYSeguridadAdmin(u, password);
    }

    @PostMapping("/imagenPerfil")
    public void imagenPerfil(Model m, Integer id, MultipartFile ficheroImagen) {
        String nombre = UUID.randomUUID().toString();
        String nombreFichero = ficheroImagen.getOriginalFilename().toLowerCase();
        String extension = nombreFichero.substring(nombreFichero.lastIndexOf("."));
        String subir = rutaBase + nombre + extension;
        File f = new File(subir);
        f.getParentFile().mkdirs();
        System.out.println("error" + subir);
        try {
            Files.copy(ficheroImagen.getInputStream(), f.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Usuario user = serviceUsuario.usuarioId(id);
            user.setUriFoto(subir);

            serviceUsuario.editarUsuario(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> mostrarImagen(String imagen) {

        return serviceUsuario.visualizarImagen(imagen);
    }
    
    @GetMapping("/usuarioSeguridadMail")
    public Usuario usuarioSeguridadMail(String mail){
        return serviceUsuario.usuarioRegistrado(mail);
    }
}
