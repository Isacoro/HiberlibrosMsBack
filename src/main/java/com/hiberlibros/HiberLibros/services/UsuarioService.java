package com.hiberlibros.HiberLibros.services;

import com.hiberlibros.HiberLibros.entities.Usuario;
import com.hiberlibros.HiberLibros.interfaces.ISeguridadService;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioLibroService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hiberlibros.HiberLibros.repositories.UsuarioRepository;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioService;
import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ISeguridadService seguridadService;
    @Autowired
    private IUsuarioLibroService usuarioLibroService;

    @Override
    public String guardarUsuarioYSeguridad(Usuario u, String password) {
        String resultado = guardarUsuario(u);
        if (resultado.equals("Usuario registrado con éxito")) {
            seguridadService.altaUsuarioSeguridad(u.getMail(), u.getId(), password, "Usuario");
        }
        return resultado;
    }

    @Override
    public String guardarUsuarioYSeguridadAdmin(Usuario u, String password) {
        String resultado = guardarUsuario(u);
        if (resultado.equals("Usuario registrado con éxito")) {
            seguridadService.altaUsuarioSeguridad(u.getMail(), u.getId(), password, "Administrador");
        }
        return resultado;
    }

    @Override
    public String guardarUsuario(Usuario u) {
        String resultado = "";
        int auxMail = u.getMail().indexOf("@");
        String mailSubstring = u.getMail().substring(auxMail);//para comprobar si el mail tiene buen formato

        if (u.getNombre() == null || u.getApellido() == null || u.getDireccion() == null || u.getCiudad() == null || u.getMail() == null || u.getTelef() == null) {
            resultado = "Error: Campo requerido vacío";

        } else if (!mailSubstring.contains(".")) { //@blabla. si no punto consideramos que no esta bien
            resultado = "Error: e-mail incorrecto";

        } else if (usuarioRepository.findByMailContainsIgnoreCase(u.getMail()).isPresent()) {//su ya existe ese mail
            Usuario uAux = usuarioRepository.findByMailContainsIgnoreCase(u.getMail()).get();

            if (uAux.getDesactivado()) {
                uAux.setDesactivado(Boolean.FALSE);
                usuarioRepository.save(uAux);
                resultado = "Usuario registrado con éxito";

            } else if (!uAux.getDesactivado()) {
                resultado = "Error: Ya existe un usuario registrado con ese e-mail";
            }
        } else {
            u.setDesactivado(Boolean.FALSE);
            usuarioRepository.save(u);
            resultado = "Usuario registrado con éxito";
        }
        return resultado;
    }

    @Override
    public Boolean borrarUsuario(Integer id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            Boolean result = usuarioLibroService.usuarioBorrado(usuario.get());
            if (result) {
                usuario.get().setDesactivado(Boolean.TRUE);
                usuarioRepository.save(usuario.get());
                seguridadService.bajaUsuarioSeguridadPorMail(usuario.get().getMail());
            }
            return result;
        } else {
            return false;
        }
    }

    @Override
    public List<Usuario> usuariosList() {
        return usuarioRepository.findByDesactivado(Boolean.FALSE);
    }

    @Override
    public boolean registrado(String mail) { //comprueba si existe ese usuario por mail
        if (usuarioRepository.findByMail(mail).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Usuario usuarioRegistrado(String mail) {
        return usuarioRepository.findByMail(mail).get();
    }

    @Override
    public String editarUsuario(Usuario usr) {
        usuarioRepository.save(usr);
        return usr.getMail();
    }

    @Override
    public Usuario usuarioId(Integer id) {
        return usuarioRepository.findByIdAndDesactivado(id, Boolean.FALSE).get();
    }

    @Override
    public Integer contarUsuarios() {
        return usuarioRepository.countByDesactivado(Boolean.FALSE);
    }

    @Override
    public ResponseEntity<Resource> visualizarImagen(String imagen){
        File file = new File(imagen);
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Disposition", "attachment;");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        try {
            
            InputStreamResource resource = new InputStreamResource(new FileInputStream(imagen));
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
}
