package com.hiberlibros.HiberLibros.services;

import com.hiberlibros.HiberLibros.repositories.UsuarioSeguridadRepository;
import com.hiberlibros.HiberLibros.repositories.UsuarioRepository;
import com.hiberlibros.HiberLibros.entities.Usuario;
import com.hiberlibros.HiberLibros.entities.UsuarioSeguridad;
import com.hiberlibros.HiberLibros.dtos.UsuarioSeguridadDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ValidacionService implements UserDetailsService {

    @Autowired
    private UsuarioSeguridadRepository repoUsuSeg;

    @Autowired
    private UsuarioRepository repoUsu;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] usuarios = username.split(",");
        Optional<Usuario> usuario = repoUsu.findByMail(usuarios[0]);
        if (usuario.isPresent()) {

            Optional<UsuarioSeguridad> usuarioSeguridad = repoUsuSeg.findByIdUsuario(usuario.get().getId());

            if (usuario.isPresent()) {

                UsuarioSeguridadDto obj = new UsuarioSeguridadDto();
                obj.setUserId(usuarioSeguridad.get().getIdUsuario());
                obj.setUsername(usuarioSeguridad.get().getMail());
                obj.setPassword(usuarioSeguridad.get().getPassword());
                List<SimpleGrantedAuthority> roles = usuarioSeguridad.get().getRoles()
                        .stream()
                        .map(x -> new SimpleGrantedAuthority("ROLE_" + x.getNombre_rol()))
                        .collect(Collectors.toList());
                obj.setRoles(roles);
                return obj;
            } else {
                throw new UsernameNotFoundException("Usuario/Password incorrecto");
            }

        } else {
            throw new UsernameNotFoundException("Usuario/Password incorrecto");
        }
    }
    

}
