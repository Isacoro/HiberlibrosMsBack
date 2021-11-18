package com.hiberlibros.HiberLibros.repositories;

import com.hiberlibros.HiberLibros.entities.Genero;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Isabel
 */
public interface GeneroRepository extends JpaRepository<Genero, Integer> {
    
    public List<Genero> findByDesactivado(Boolean b); 

}
