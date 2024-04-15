/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sisinfo2poryectopp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import javax.swing.*;
/**
 * @author Sidney Angelly Sahonero Garrado
 */
public class Conexion {
    Connection conex = null;
    String usuario = "sisinfo2_user";//"postgres";
    String contrasena = "MjJfeyXg5JB1hbCf0vYs14Gmw8MVjo3N";//"sidneyPOSTGRE24";
    String cadena = "jdbc:postgresql://dpg-coal268l6cac73emur1g-a.oregon-postgres.render.com/sisinfo2";
    
    public Connection Conectar(){
        try {
            Class.forName("org.postgresql.Driver");
            conex = DriverManager.getConnection(cadena, usuario, contrasena);
            JOptionPane.showMessageDialog(null, "Se conecto correctamente a la BD.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectar la base de datos.");
        }
        return conex;
    }  

    public String recupNombre(int id){
        Conectar();
        String nombreEst = "";
        try{
            String sql ="select nombre from usuario where id_usuario = ?";
            PreparedStatement ejecutar = conex.prepareStatement(sql);
            ejecutar.setInt(1, id);
            ResultSet resultado = ejecutar.executeQuery();
            if (resultado.next()) {
                nombreEst = resultado.getString("nombre");
            } else {
                JOptionPane.showMessageDialog(null, "Error al identificarte");
            }
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al recuperar nombre.");
        }   
        return nombreEst;
    }
    public String recupCorreo(int id){
        Conectar();
        String correoEst = "";
        try{
            String sql ="select email from usuario where id_usuario = ?";
            PreparedStatement ejecutar = conex.prepareStatement(sql);
            ejecutar.setInt(1, id);
            ResultSet resultado = ejecutar.executeQuery();
            if (resultado.next()) {
                correoEst = resultado.getString("email");
            } else {
                JOptionPane.showMessageDialog(null, "Error al identificarte");
            }
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al recuperar correo.");
        }   
        return correoEst;
    }
    public Boolean ExistClase(String codigoClass){
        Conectar();
        boolean result = false;
        try{
            String sql ="select id_materia from materia where id_materia = ?";
            PreparedStatement ejecutar = conex.prepareStatement(sql);
            ejecutar.setString(1, codigoClass);
            ResultSet resultado = ejecutar.executeQuery();
            if (resultado.next()) {
                result = true;
            } else {
                JOptionPane.showMessageDialog(null, "La clase no existe");
            }
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al recuperar codigo.");
        }   
        return result;
    }

    public void registrarEstEnClase(int idAlumno,String idMateria) {
        Conectar();
        
         try{
            String sql ="INSERT INTO inscripcion (id_inscripcion, id_estudiante, id_materia)"
                    + "SELECT ?,?,? WHERE NOT EXISTS (SELECT id_inscripcion FROM inscripcion WHERE id_inscripcion = ?) ";
            PreparedStatement ejecutar = conex.prepareStatement(sql);
            ejecutar.setString(1, idAlumno+idMateria);
            ejecutar.setInt(2, idAlumno);
            ejecutar.setString(3, idMateria);
            ejecutar.setString(4, idAlumno+idMateria);
            int result = ejecutar.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Te uniste a una nueva clase.");
            } else {
                JOptionPane.showMessageDialog(null, "Ya estas inscrito en esta clase.");
            }
            
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al ingresar a la clase.");
        }
    }
    
    public ArrayList<String> anuncios(){
        ArrayList<String> anuncio = new ArrayList<>();
        Conectar(); 
        try{
            String sql1 = "SELECT * FROM anuncio ORDER BY fecha_anuncio DESC";
            String sql2 = "SELECT nombre_materia FROM materia WHERE id_materia = ?";
            PreparedStatement ejecutar1 = conex.prepareStatement(sql1);
            PreparedStatement ejecutar2 = conex.prepareStatement(sql2);
            ResultSet resultado1 = ejecutar1.executeQuery();
        
            int i = 0; // Variable para llevar el control del índice en el array de anuncios
            while (resultado1.next() && i < 100) { // Utilizamos un bucle while para iterar sobre los resultados
                String idMateria = resultado1.getString("id_materia");
                ejecutar2.setString(1, idMateria);
                ResultSet resultado2 = ejecutar2.executeQuery();
            
                //Verificamos si se obtuvo un resultado de la consulta
                 if (resultado2.next()){
                    String nomMat = resultado2.getString("nombre_materia");
                    System.out.print(nomMat);
                    String descrip = resultado1.getString("descripcion_anuncio");
                    Time hora = resultado1.getTime("hora_anuncio");
                    Date fecha = resultado1.getDate("fecha_anuncio");
                    String anunciotext = "<html>"+"<br>MATERIA:"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ nomMat+"<br>"+fecha
                            +" a las "+hora+"<br>DESCRIPCIÓN:"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+descrip+"<br>"+"."+"</html>";
                    anuncio.add(anunciotext);
                     // Incrementamos el índice del array
                 }
                 i++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Imprimimos la traza de la excepción para depuración
            JOptionPane.showMessageDialog(null, "Error al recuperar anuncios.");
        } 
    
        return anuncio;
    }
    
    ArrayList<String> comentarios(int idEstudiante) {
        ArrayList<String> comentarios = new ArrayList<>();
        Conectar(); 
        try{
            String sql1 = "SELECT * FROM comentario WHERE id_estudiante=? ORDER BY fecha_comentario DESC";
            String sql2 = "SELECT nombre_tarea FROM tarea WHERE id_tarea = ?";
            String sql3 = "SELECT nombre FROM usuario u, docente d WHERE u.id_usuario=d.id_usuario and id_docente=?";
            PreparedStatement ejecutar1 = conex.prepareStatement(sql1);
            PreparedStatement ejecutar2 = conex.prepareStatement(sql2);
            PreparedStatement ejecutar3 = conex.prepareStatement(sql3);
            ejecutar1.setInt(1, idEstudiante);
            ResultSet resultado1 = ejecutar1.executeQuery();
        
            int i = 0; // Variable para llevar el control del índice en el array de anuncios
            while (resultado1.next() && i < 100) { // Utilizamos un bucle while para iterar sobre los resultados
                int idTarea = resultado1.getInt("id_tarea");
                int idDocente = resultado1.getInt("id_docente");
                ejecutar2.setInt(1, idTarea);
                ejecutar3.setInt(1, idDocente);
                ResultSet resultado2 = ejecutar2.executeQuery();
                ResultSet resultado3 = ejecutar3.executeQuery();
            
                //Verificamos si se obtuvo un resultado de la consulta
                if (resultado2.next() && resultado3.next()){
                    String nomTarea = resultado2.getString("nombre_tarea");
                    String nomDocente = resultado3.getString("nombre");
                    Time hora = resultado1.getTime("hora_comentario");
                    Date fecha = resultado1.getDate("fecha_comentario");
                    String contenido = resultado1.getString("contenido");
                    String coment = "<html>"+"<br>TAREA:"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ nomTarea + "<br> DOCENTE:"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                            +nomDocente + "<br>"+ fecha + " a las " + hora + "<br> COMENTARIO:"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ contenido + "<br>"+"."+ "</html>";
                    comentarios.add(coment);
                    i++; // Incrementamos el índice del array
                }
            }
        } catch (SQLException ex) { 
            // Imprimimos la traza de la excepción para depuración
            JOptionPane.showMessageDialog(null, "Error al recuperar anuncios.");
        } 
        return comentarios;
    }
    
    ArrayList<String> correosEst(String idMateria) {
        ArrayList<String> correos = new ArrayList<>();
        Conectar(); 
        try{
            String sql = "SELECT email FROM estudiante e,usuario u, inscripcion i "
                    + "WHERE e.id_usuario=u.id_usuario and i.id_estudiante=e.id_estudiante and i.id_materia=?";
            PreparedStatement ejecutar = conex.prepareStatement(sql);
            ejecutar.setString(1, idMateria);
            ResultSet resultado = ejecutar.executeQuery();
        
            int i = 0; // Variable para llevar el control del índice en el array de anuncios
            while (resultado.next()) { // Utilizamos un bucle while para iterar sobre los resultados
                String correo = resultado.getString("email");
                
                correos.add(correo);
                    i++; // Incrementamos el índice del array
                
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Imprimimos la traza de la excepción para depuración
            JOptionPane.showMessageDialog(null, "Error al recuperar anuncios.");
        } 
        return correos;
    }
    public String nombreUserConCorreo(String correo){
        String nomUser="";
        Conectar();
        try{
            String sql ="SELECT nombre FROM usuario WHERE email = ?";
            PreparedStatement ejecutar = conex.prepareStatement(sql);
            ejecutar.setString(1, correo);
            ResultSet resultado = ejecutar.executeQuery();
            if (resultado.next()) {
                nomUser = resultado.getString("nombre");
            } else {
                JOptionPane.showMessageDialog(null, "Error al identificar alumno");
            }
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al recuperar nombre.");
        }   
        return nomUser;
    }

}