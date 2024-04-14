/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sisinfo2;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author hp
 */
public class ConexionBD {
    Connection conectar =null;
    String usuario="sisinfo2_user";
    String contrasenia ="MjJfeyXg5JB1hbCf0vYs14Gmw8MVjo3N";
    String bd="sisinfo2";
    String puerto ="5432";
    
    String cadena = "jdbc:postgresql://dpg-coal268l6cac73emur1g-a.oregon-postgres.render.com/sisinfo2";
    
    public Connection conexionEstablecida() throws SQLException{
      try{
          Class.forName("org.postgresql.Driver");
          conectar =DriverManager.getConnection(cadena,usuario,contrasenia);
      }catch(HeadlessException | ClassNotFoundException | SQLException e){
        JOptionPane.showMessageDialog(null, "No se conecto a la base de datos,error:"+ e.toString());
      }
      return conectar;
    }
}
