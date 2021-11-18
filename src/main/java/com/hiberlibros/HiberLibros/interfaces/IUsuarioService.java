package com.hiberlibros.HiberLibros.interfaces;

import com.hiberlibros.HiberLibros.entities.Usuario;
import java.io.IOException;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Usuario
 */
public interface IUsuarioService {

    public String guardarUsuario(Usuario u);

    public String editarUsuario(Usuario u);

    public Boolean borrarUsuario(Integer id);

    public List<Usuario> usuariosList();

    public boolean registrado(String mail);

    public Usuario usuarioRegistrado(String mail);

    public Usuario usuarioId(Integer id);

    public String guardarUsuarioYSeguridad(Usuario u, String password);

    public String guardarUsuarioYSeguridadAdmin(Usuario u, String password);
    
    public Integer contarUsuarios();

    public ResponseEntity<Resource> visualizarImagen(String imagen);

}
