/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiberlibros.HiberLibros.interfaces;

import com.hiberlibros.HiberLibros.entities.Peticion;
import com.hiberlibros.HiberLibros.entities.Usuario;
import com.hiberlibros.HiberLibros.entities.UsuarioLibro;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface IPeticionService {

    public List<Peticion> consultaTodasPeticiones();

    public Peticion consultarPeticionId(Integer id);

    public void insertaPeticion(Peticion p, Integer id_ul, Usuario u);

    public void insertaModificaPeticion(Peticion p);

    public void eliminaPeticion(Peticion p);

    public void eliminarId(Integer id);

    public void aceptarPeticion(Peticion p);

    public void rechazarPeticion(Integer id);

    public List<Peticion> consultarPeticionesPendientes(Usuario u);

    public List<Peticion> consultarPeticionesAceptadas(Usuario u);

    public List<Peticion> consultarPeticionesRechazadas(Usuario u);

    public List<Peticion> consutarPeticionesUsuarioPendientes(Usuario u);

    public List<Peticion> consultarPeticonesRecibidas(Usuario u);
    
    public void borrarPorUsuarioSolicitante(Usuario u);
    
    public void borrarPorUsuarioLibro(UsuarioLibro ul);

}
