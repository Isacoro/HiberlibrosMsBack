package com.hiberlibros.HiberLibros.repositories;

import com.hiberlibros.HiberLibros.entities.Relato;
import com.hiberlibros.HiberLibros.entities.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelatoRepository extends JpaRepository<Relato, Integer> {

    public List<Relato> findByUsuario(Usuario u);

    public List<Relato> findByTituloContainingIgnoreCase(String titulo);

    public Relato findByFichero(String fichero);

}
