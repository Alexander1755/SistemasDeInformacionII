/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sidney;

import java.sql.*;
import java.util.Properties;
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
    private String correosDestino, asunto, mensaje, nombreAlumn;
    private Properties propiedad = new Properties();
    ResultSet resultado;
    private String correoOrigen = "....com";//"notificaciones@adfns.com";
    private String passwordOrigen = "...";//"ADFNSappSisInfo2_2024";
    
    public void EnviarCorreoATodos(String asunt){ 
        conect.Conectar();
        try{
            String sql = "select distinct correo from estudiante;";
            PreparedStatement ejecutar = conect.prepareStatement(sql);
            resultado = ejecutar.executeQuery();
            
            for(int i=0; resultado.next(); i++){
                correosDestino = resultado.getString("correo");
                EnviarCorreo(correosDestino,asunt);
            }
            
        }catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al recuperar la lista de platos de la base de datos.");
        }     
    }
    
    public void EnviarCorreo(String correoDest, String asunt) throws SQLException{
        correosDestino = correoDest;
        
        String sql = "select nombre from estudiante where correo = ?;";
        PreparedStatement ejecutar = conect.prepareStatement(sql);
        ejecutar.setString(1, correosDestino);
        resultado = ejecutar.executeQuery();
        nombreAlumn = resultado.getString("nombre");
        
        if(asunto.equals("NotaTarea")){
            asunto = "Nueva calificación";
            mensaje = "Hola"+nombreAlumn+"recibiste una nueva calificacion de parte de tu docente, en la aplicación";
        }else if(asunto.equals("NewTarea")){
            asunto = "Nueva Tarea";
            mensaje = "Hola"+nombreAlumn+"recibiste una nueva tarea de parte de tu docente, en la aplicación";;
        }else if(asunto.equals("ComentDocent")){
            asunto = "Nuevo Comentario en tu Tarea";
            mensaje = "Hola"+nombreAlumn+"recibiste una nuevo comentario de tu docente, en la aplicación";;
        }else if(asunto.equals("NewAnuncio")){
            asunto = "Nuevo Anuncio";
            mensaje = "Hola"+nombreAlumn+"Tu docente publico un nuevo anuncio en la aplicación, corre  verlo";;
        }
        
         propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
         propiedad.setProperty("mail.smtp.starttls.enable", "true");
         propiedad.setProperty("mail.smtp.port", "587");
         propiedad.setProperty("mail.smtp.auth", "true");
         
        Session sesion = Session.getDefaultInstance(propiedad);
        MimeMessage mail = new MimeMessage(sesion);
        try {
            mail.setFrom(new InternetAddress (correosDestino));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress (correosDestino));
            mail.setSubject(asunto);
            mail.setText(mensaje);
            
            Transport transportar = sesion.getTransport("smtp");
            transportar.connect(correoOrigen,passwordOrigen);
            transportar.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));          
            transportar.close();
            
            JOptionPane.showMessageDialog(null, "Mensaje enviado con exito");
            
            
        } catch (AddressException ex) {
            JOptionPane.showMessageDialog(null, "Error de coneccion a correo.");
        } catch (MessagingException ex) {
            JOptionPane.showMessageDialog(null, "Error de envio de mensaje.");
        }

    }

    
    
}

