package com.hiberlibros.HiberLibros.repositories;

import com.hiberlibros.HiberLibros.entities.Editorial;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Usuario
 */
public interface EditorialRepository extends JpaRepository<Editorial, Integer> {

    public Optional<Editorial> findByIdAndDesactivado(Integer id, Boolean b);
    public List<Editorial> findByDesactivado(Boolean desactivado);
    public List<Editorial> findByNombreEditorialIgnoreCaseAndDesactivado(String nombreEditorial, Boolean b);
}
