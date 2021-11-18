package com.hiberlibros.HiberLibros.repositories;

import com.hiberlibros.HiberLibros.entities.ForoLibro;
import com.hiberlibros.HiberLibros.entities.Libro;
import com.hiberlibros.HiberLibros.entities.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Usuario
 */
public interface ForoLibroRepository extends JpaRepository<ForoLibro, Integer> {

    public List<ForoLibro>findByDesactivado(Boolean desactivado);
    public List<ForoLibro> findByIdLibro(Integer idLibro);
    public List<ForoLibro> findByIdLibroAndDesactivado(Libro libro, Boolean desactivado);
    public List<ForoLibro> findByUsuarioCreadorAndDesactivado(Usuario usuario, Boolean desactivado);
    public Optional<ForoLibro> findByIdAndDesactivado(Integer id, Boolean desactivado);
}
