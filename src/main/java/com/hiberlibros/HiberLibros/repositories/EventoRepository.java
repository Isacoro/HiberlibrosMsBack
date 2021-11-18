/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiberlibros.HiberLibros.repositories;

import com.hiberlibros.HiberLibros.entities.Evento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
    public List<Evento> findBySummaryContainingIgnoreCase(String search);
    
}
//th:each en java script
//<script th:inline="javascript">
//        
//        /*[# th:each="mov : ${movimientos}"]*/
//            var a=/*[[${mov.cantidad}]]*/;
//        /*[/]*/
//        
//    </script>
