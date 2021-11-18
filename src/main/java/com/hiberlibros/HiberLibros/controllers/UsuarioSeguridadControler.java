/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.entities.Rol;
import com.hiberlibros.HiberLibros.entities.UsuarioSeguridad;
import com.hiberlibros.HiberLibros.repositories.RolRepository;
import com.hiberlibros.HiberLibros.repositories.UsuarioSeguridadRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarioSeguridadback")
public class UsuarioSeguridadControler {
    @Autowired
    public UsuarioSeguridadRepository repoUsuarioSegu;
    
    @Autowired
    public RolRepository repoRol;
    
    @GetMapping
    public UsuarioSeguridad usuarioSeguridadSecurity(Integer id){
        return repoUsuarioSegu.findByIdUsuario(id).get();
    }
    
    
}
