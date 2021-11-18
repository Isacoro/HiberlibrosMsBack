package com.hiberlibros.HiberLibros.repositories;

import com.hiberlibros.HiberLibros.entities.ComentarioForo;
import com.hiberlibros.HiberLibros.entities.ForoLibro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ComentarioForoRepository extends JpaRepository<ComentarioForo, Integer> {
    
    public void deleteByForoLibro(ForoLibro fl);

    public List<ComentarioForo> findByForoLibro(ForoLibro foroLibro);
    
}
