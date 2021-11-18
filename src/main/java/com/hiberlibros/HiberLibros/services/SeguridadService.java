package com.hiberlibros.HiberLibros.services;

import com.hiberlibros.HiberLibros.dtos.UsuarioSeguridadDto;
import com.hiberlibros.HiberLibros.entities.Rol;
import com.hiberlibros.HiberLibros.entities.UsuarioSeguridad;
import com.hiberlibros.HiberLibros.interfaces.ISeguridadService;
import com.hiberlibros.HiberLibros.repositories.RolRepository;
import com.hiberlibros.HiberLibros.repositories.UsuarioSeguridadRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class SeguridadService implements ISeguridadService {

    @Autowired
    private UsuarioSeguridadRepository repoUsuSeg;

    @Autowired
    private UsuarioService serviceUsuario;

    @Autowired
    private RolRepository repoRol;

    @Autowired
    private PasswordEncoder passEncoder;

    @Transactional
    public String altaUsuarioSeguridad(String mail, Integer idUsuario, String password, String nombre_rol) {
        Optional<UsuarioSeguridad> usuAplic = repoUsuSeg.findByIdUsuario(idUsuario);
        if (usuAplic.isPresent()) {
            List<Rol> roles = usuAplic.get().getRoles();
            List<Rol> rolesFiltrados = roles.stream().filter(x -> x.getNombre_rol().equals(nombre_rol)).collect(Collectors.toList());
            if (rolesFiltrados.size() > 0) {
                return "error: alta no realizada: usuario con este Rol activado previamente";
            }
        }

        UsuarioSeguridad u = new UsuarioSeguridad();
        u.setIdUsuario(idUsuario);
        u.setMail(mail);
        u.setPassword(passEncoder.encode(password));
        repoUsuSeg.save(u);

        Rol r = new Rol();
        r.setIdUsuario(u);
        r.setNombre_rol(nombre_rol);
        repoRol.save(r);

        return "ok";

    }

    @Transactional
    public long bajaUsuarioSeguridad(Integer idUsuarioSeguridad) {
        repoUsuSeg.deleteById(idUsuarioSeguridad);
        Long elementosBorrados = new Long(0);
        // Long elementosBorrados = repoRol.deleteByIdUsuarioSeguridad(idUsuarioSeguridad);
        return elementosBorrados;
    }

    public long bajaUsuarioSeguridadPorMail(String mailUsuarioSeguridad) {
        Optional<UsuarioSeguridad> usuarioSeguridad = repoUsuSeg.findByMail(mailUsuarioSeguridad);
        if (usuarioSeguridad.isPresent()) {
            Optional<Rol> r = repoRol.findByIdUsuario(usuarioSeguridad.get());
            if (r.isPresent()) {
                repoRol.deleteById(r.get().getId());

            }
            return bajaUsuarioSeguridad(usuarioSeguridad.get().getId());
        }
        return 0;
    }

    public String getMailFromContext() {
        return ((UsuarioSeguridadDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
    
    public Integer getIdUsuarioFromContext(){
        return ((UsuarioSeguridadDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
    }
}
