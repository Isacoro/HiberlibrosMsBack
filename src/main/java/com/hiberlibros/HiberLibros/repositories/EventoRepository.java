package com.hiberlibros.HiberLibros.repositories;

import com.hiberlibros.HiberLibros.entities.Evento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Integer> {

    public List<Evento> findBySummaryContainingIgnoreCase(String search);
    
}

