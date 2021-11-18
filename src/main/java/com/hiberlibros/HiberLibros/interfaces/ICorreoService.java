/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiberlibros.HiberLibros.interfaces;

/**
 *
 * @author Usuario
 */
public interface ICorreoService {
    public String enviarCorreo(String destinatario,String asunto, String cuerpo );
}
