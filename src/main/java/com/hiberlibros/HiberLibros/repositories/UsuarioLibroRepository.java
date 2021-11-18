package com.hiberlibros.HiberLibros.repositories;

import com.hiberlibros.HiberLibros.entities.Libro;
import com.hiberlibros.HiberLibros.entities.Usuario;
import com.hiberlibros.HiberLibros.entities.UsuarioLibro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Usuario
 */
public interface UsuarioLibroRepository extends JpaRepository<UsuarioLibro, Integer> {

    public List<UsuarioLibro> findByUsuario(Usuario u);

    public List<UsuarioLibro> findByUsuarioAndQuieroTengoAndEstadoPrestamo(Usuario u, String quieroTengo, String estadoPrestamo);

    public List<UsuarioLibro> findByLibroAndQuieroTengoAndEstadoPrestamo(Libro l, String quieroTengo, String estadoPrestamo);

    public List<UsuarioLibro> findByUsuarioAndDesactivadoOrderByQuieroTengoAsc(Usuario u, Boolean b); 

    public List<UsuarioLibro> findByUsuarioNotAndQuieroTengoAndEstadoPrestamoAndDesactivado(Usuario u, String quieroTengo, String estadoPrestamo, Boolean b);
    
    public List<UsuarioLibro> findByLibroAndDesactivadoAndEstadoPrestamo(Libro l, Boolean desactivado, String estadoPrestamo);
    
    public List<UsuarioLibro> findByLibroAndDesactivado(Libro l, Boolean desactivado);
    
    public List<UsuarioLibro> findByUsuarioAndDesactivado(Usuario u, Boolean desactivado);
    
    public List<UsuarioLibro> findByUsuarioAndDesactivadoAndEstadoPrestamo(Usuario u, Boolean desactivado, String estadoPrestamo);
    
    //public List<UsuarioLibro> findByUsuarioNotAndQuieroTengoAndEstadoPrestamoAndDesactivado(Usuario u, String quieroTengo, String estadoPrestamo, Boolean b);
    
    public Integer countByUsuarioAndDesactivado(Usuario usuario, Boolean b);

}
