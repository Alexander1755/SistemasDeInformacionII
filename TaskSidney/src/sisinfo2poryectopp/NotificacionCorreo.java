/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sisinfo2poryectopp;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
/**
 * @authora Sidney Angelly Sahonero Garrado
 */
public class NotificacionCorreo {
    private Conexion conect = new Conexion();
    private String mensaje, asunto;
    private Properties propiedad = new Properties();
    private String correoOrigen = "appsubirtareas@gmail.com";
    private String passwordOrigen = "a w z z g q f t w h z f v q k c";
    
    public void EnviarCorreoATodos(String asunt,String idMat){ 
        ArrayList<String> emails = conect.correosEst(idMat);
        for (String email : emails){
            try {
                EnviarCorreo(email,asunt);
            } catch (SQLException ex) {
                Logger.getLogger(NotificacionCorreo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                Logger.getLogger(NotificacionCorreo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void EnviarCorreo(String correoDest, String asunt) throws SQLException, MessagingException{
        String nameAlumn = conect.nombreUserConCorreo(correoDest);
        switch (asunt) {
            case "NotaTarea":
                asunto = "Nueva Calificacion Disponible";
                mensaje = "Hola "+nameAlumn+"recibiste una nueva calificacion de parte de tu docente, en la aplicación";
                break;
            case "NewTarea":
                asunto = "Tienes una Nueva Tarea";
                mensaje = "Hola "+nameAlumn+"recibiste una nueva tarea de parte de tu docente, en la aplicación";
                ;
                break;
            case "ComentDocent":
                asunto = "Recibiste un Nuevo Comentario  de tu Docente";
                mensaje = "Hola"+nameAlumn+"recibiste una nuevo comentario de tu docente, en la aplicación";
                ;
                break;
            case "NewAnuncio":
                asunto = "Tu Docente Pubico un Nuevo Anuncio";
                mensaje = "Hola "+nameAlumn+"Tu docente publico un nuevo anuncio en la aplicación, corre  verlo";
                ;
                break;
            default:
                break;
        }
        
         propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
         propiedad.setProperty("mail.smtp.starttls.enable", "true");
         propiedad.setProperty("mail.smtp.port", "587");
         propiedad.setProperty("mail.smtp.auth", "true");
         
        Session sesion = Session.getDefaultInstance(propiedad);
        MimeMessage mail = new MimeMessage(sesion);
        try {
            mail.setFrom(new InternetAddress (correoDest));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress (correoDest));
            mail.setSubject(asunto);
            mail.setText(mensaje);
            
            Transport transportar = sesion.getTransport("smtp");
            transportar.connect(correoOrigen,passwordOrigen);
            transportar.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));          
            transportar.close();
            
            JOptionPane.showMessageDialog(null, "Email enviado con exito");    
        } catch (AddressException ex) {
            JOptionPane.showMessageDialog(null, "Error de coneccion a correo.");
        } 
    }    
}

