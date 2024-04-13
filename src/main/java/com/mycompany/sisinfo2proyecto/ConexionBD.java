/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sisinfo2proyecto;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Fernanda
 */
public class ConexionBD {
    private String usuario = "sisinfo2_user";
    private String contrasenia = "MjJfeyXg5JB1hbCf0vYs14Gmw8MVjo3N";
    private String bd = "sisinfo2";
    private String puerto = "5432";
    private String cadena = "jdbc:postgresql://dpg-coal268l6cac73emur1g-a.oregon-postgres.render.com/sisinfo2";

    public Connection conexionEstablecida() {
        Connection conectar = null;
        try {
            Class.forName("org.postgresql.Driver");
            conectar = DriverManager.getConnection(cadena, usuario, contrasenia);
            JOptionPane.showMessageDialog(null, "Se conectó a la base de datos");
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "No se conectó a la base de datos,error:" + e.toString());
        }
        return conectar;
    }
    
}
