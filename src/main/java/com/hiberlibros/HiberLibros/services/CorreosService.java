/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiberlibros.HiberLibros.services;

import com.hiberlibros.HiberLibros.interfaces.ICorreoService;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;


/**
 *
 * @author Usuario
 */
@Service
public class CorreosService implements ICorreoService{
    public String enviarCorreo(String destinatario,String asunto, String cuerpo ) {
    //String destinatario =  "jorgemartinms@gmail.com"; //A quien le quieres escribir.
    //String asunto = "peticion de intercambio aceptada";
    //String cuerpo = "la peticion de su libro ha sido aceptada, pase a intercambiarlo";

    enviarConGMail(destinatario, asunto, cuerpo);
    return "enviado";
    }
    
    public String enviarConGMail(String destinatario, String asunto, String cuerpo) {
    // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
    String remitente = "hiberuslibros@gmail.com";  //Para la dirección nomcuenta@gmail.com

    Properties props = System.getProperties();
    props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
    props.put("mail.smtp.user", remitente);
    props.put("mail.smtp.clave", "hiberlibros1!");    //La clave de la cuenta
    props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
    props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
    props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

    Session session = Session.getDefaultInstance(props);
    MimeMessage message = new MimeMessage(session);

    try {
        message.setFrom(new InternetAddress(remitente));
        message.addRecipients(Message.RecipientType.TO, destinatario);//Se podrían añadir varios de la misma manera
        message.setSubject(asunto);
        message.setText(cuerpo);
        javax.mail.Transport transport = session.getTransport("smtp");
        transport.connect("smtp.gmail.com", remitente, props.getProperty("mail.smtp.clave"));
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        return "enviado";
    }
    catch (MessagingException me) {
        me.printStackTrace();   //Si se produce un error
        return "no enviado";
    }
}
}
