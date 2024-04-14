/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sisinfo2;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class anunciosClase extends JFrame {
    
    public anunciosClase() {
        // Configuración básica de la ventana
        setTitle("Anuncios");
        // Ajustar el tamaño de la ventana
        setSize(600, 400);
        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Crear un panel para agregar componentes
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Establecer un diseño vertical
        
        // Crear un panel de desplazamiento
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        // Agregar el panel de desplazamiento a la ventana
        add(scrollPane);
        
        // Obtener los anuncios de la base de datos y agregarlos al panel
        obtenerAnuncios(panel);
        
        // Hacer visible la ventana
        setVisible(true);
    }
    
    private void obtenerAnuncios(JPanel panel) {
        ConexionBD conexion = new ConexionBD();
        try {
            Connection conn = conexion.conexionEstablecida();
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String query = "SELECT anuncios FROM materias";
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    String anuncio = rs.getString("anuncios");
                    JTextArea textArea = new JTextArea(anuncio);
                    textArea.setEditable(false); // Para que el usuario no pueda editar el texto
                    textArea.setLineWrap(true); // Envolver el texto si es necesario
                    textArea.setWrapStyleWord(true); // Envolver palabras enteras
                    panel.add(textArea);
                    panel.add(Box.createRigidArea(new Dimension(0, 10))); // Agregar espacio entre anuncios
                }
                conn.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener los anuncios de la base de datos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Crear una instancia de la ventana de anuncios
        SwingUtilities.invokeLater(() -> new anunciosClase());
    }
}

