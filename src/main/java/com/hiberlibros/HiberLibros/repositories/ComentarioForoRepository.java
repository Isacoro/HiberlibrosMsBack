/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiberlibros.HiberLibros.repositories;

import com.hiberlibros.HiberLibros.entities.ComentarioForo;
import com.hiberlibros.HiberLibros.entities.ForoLibro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Usuario
 */
public interface ComentarioForoRepository extends JpaRepository<ComentarioForo, Integer> {
    
    public void deleteByForoLibro(ForoLibro fl);
    public List<ComentarioForo> findByForoLibro(ForoLibro foroLibro);
    
}
