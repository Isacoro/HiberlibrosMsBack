package com.hiberlibros.HiberLibros.entities;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Usuario
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "intercambios")
public class Intercambio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaPrestamo;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaDevolucion;
    
    @ManyToOne
    @JoinColumn(name="id_usuario_libro_prestador")
    private UsuarioLibro usuarioPrestador;
    
    @ManyToOne
    @JoinColumn(name="id_usuario_libro_prestatario")
    private UsuarioLibro usuarioPrestatario;
    


}
