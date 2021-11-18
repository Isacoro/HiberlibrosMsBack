package com.hiberlibros.HiberLibros.repositories;

import com.hiberlibros.HiberLibros.entities.Genero;
import com.hiberlibros.HiberLibros.entities.Preferencia;
import com.hiberlibros.HiberLibros.entities.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PreferenciaRepository extends JpaRepository<Preferencia, Integer> {

    public List<Preferencia> findAll();

    public List<Preferencia> findByUsuario(Usuario idUsuario);
    
    public List<Preferencia> findByGenero(Genero g);
    
    public void deleteByGenero(Genero g);
}
