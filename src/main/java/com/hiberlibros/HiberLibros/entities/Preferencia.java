package com.hiberlibros.HiberLibros.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Isabel
 */
@Data  
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="preferencias")
public class Preferencia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name="id_genero")
    private Genero genero;
    
    @ManyToOne 
    @JoinColumn(name = "id_usuario")
    private Usuario usuario; 
}
