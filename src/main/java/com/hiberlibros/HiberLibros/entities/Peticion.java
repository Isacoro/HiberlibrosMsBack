package com.hiberlibros.HiberLibros.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="peticiones")
@NoArgsConstructor
@AllArgsConstructor
public class Peticion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @OneToOne
    @JoinColumn(name = "id_usuario_libro")
    private UsuarioLibro idUsuarioLibro;
    
    @OneToOne
    @JoinColumn(name = "id_usuario_solicitante")
    private Usuario idUsuarioSolicitante;
    private Boolean aceptacion;
    private Boolean pendienteTratar;
}
