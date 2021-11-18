package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.interfaces.ISeguridadService;
import com.hiberlibros.HiberLibros.repositories.UsuarioSeguridadRepository;
import com.hiberlibros.HiberLibros.repositories.RolRepository;
import com.hiberlibros.HiberLibros.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class SeguridadController {

    @Autowired
    private UsuarioSeguridadRepository repoUsuSeg;

    @Autowired
    private UsuarioService serviceUsuario;

    @Autowired
    private RolRepository repoRol;

    @Autowired
    private PasswordEncoder passEncoder;
    
    @Autowired
    private ISeguridadService serviceSeguridad;
    
    

    @GetMapping("/url1")    //Todo el mundo
    @ResponseBody
    public String saludar1() {
        return "saluda 1";
    }

    @GetMapping("/url2")    //Todo el validados
    @ResponseBody
    public String saludar2() {
        return "saluda 2";
    }

    @GetMapping("/url3")    //Todo el adminstradores Admin.
    @ResponseBody
    public String saludar3() {
        return "saluda 3";
    }

      
//    @PostMapping("/login")
//    public String login() {
//        return "login";
//    }
    
    @GetMapping("/login2")
    public String login2() {
        return "/admin/milogin";
    }

    @GetMapping("/altaUsuarioSeguridad")
    public String altaUsuario() {
        return "/admin/adminGestion";
    }

    @PostMapping("/altaUsuarioSeguridad")
    public String altaUsuarioSeguridadPost(Model m, String mail, Integer idUsuario, String password, String nombre_rol) {
        
        String mensaje = serviceSeguridad.altaUsuarioSeguridad(mail, idUsuario, password, nombre_rol);

        if (mensaje!=null && mensaje.contains("error")){
                m.addAttribute("errMensaje", "alta no realizada: usuario con este Rol activado previamente");
                return "/admin/adminGestion";
                        
        }

        return "redirect:/admin/altaUsuarioSeguridad";

    }
    
    

    @GetMapping("/info")
    @ResponseBody
    public String info() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        final StringBuffer res = new StringBuffer();
        authentication.getAuthorities().forEach(x -> {
            res.append("," + x.getAuthority());
        });
        return authentication.getName() + res.toString();
    }
    
    @GetMapping("/logout")
    public String logout(){

        return "logout";
    }
}
